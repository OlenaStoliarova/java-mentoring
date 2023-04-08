package pl.mentoring.t2synchronizationblock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Thread-safe iteration over collection using synchronization block");
        AtomicBoolean keepOnRunning = new AtomicBoolean(true);
        List<Integer> collection = new ArrayList<>();

        Thread thread1 = new ElementProducer(collection, keepOnRunning);
        Thread thread2 = new ElementsSumator(collection, keepOnRunning);
        Thread thread3 = new ElementsCombiner(collection, keepOnRunning);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        keepOnRunning.set(false);
    }
}
