package pl.mentoring.t4_simple_object_pool.implementation;

import pl.mentoring.t4_simple_object_pool.BlockingObjectPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());

        BlockingObjectPool pool = new BlockingObjectPool(4,
            new JDBCConnectionFactory("jdbc:ucanaccess://" + jarDir + "/Test.accdb", "", ""),
            new JDBCConnectionValidator());

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        executorService.invokeAll(getTasks(pool));
        executorService.shutdown();

        pool.shutdown();
    }

    private static List<Callable<Void>> getTasks(BlockingObjectPool pool) {
        List<Callable<Void>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(useObjectFromPool(pool));
        }
        return list;
    }

    private static Callable<Void> useObjectFromPool(BlockingObjectPool pool) {
        return () -> {
            Object test = pool.get();

            try {
                Thread.sleep(300); // do something with object from pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            pool.put(test);
            return null;
        };
    }
}
