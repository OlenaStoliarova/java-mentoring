package pl.mentoring.filescanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DirStatisticsProvider implements Callable<DirStatistics> {

    private final File dir;

    public DirStatisticsProvider(File dir) {
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path");
        }
        this.dir = dir;
    }

    @Override
    public DirStatistics call() {
        int processorsCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(processorsCount);

        try {
            return pool.invoke(new DirStatisticsWorker(dir));
        } finally {
            pool.shutdown();
        }
    }

    private class DirStatisticsWorker extends RecursiveTask<DirStatistics> {

        private final File curDir;

        private DirStatisticsWorker(File dir) {
            this.curDir = dir;
        }

        @Override
        protected DirStatistics compute() {
            DirStatistics result = new DirStatistics();

            final List<DirStatisticsWorker> tasks = new ArrayList<>();
            final File[] children = curDir.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isDirectory()) {
                        DirStatisticsWorker task = new DirStatisticsWorker(child);
                        task.fork();
                        tasks.add(task);
                        result.addToDirCount(1);
                    } else if (child.isFile()) {
                        result.addToFileCount(1);
                        result.addToSize(child.length());
                    }
                }
            }

            for (DirStatisticsWorker task : tasks) {
                DirStatistics childResult = task.join();

                result.addToDirCount(childResult.getDirCount());
                result.addToFileCount(childResult.getFileCount());
                result.addToSize(childResult.getSize());
            }

            return result;
        }
    }
}
