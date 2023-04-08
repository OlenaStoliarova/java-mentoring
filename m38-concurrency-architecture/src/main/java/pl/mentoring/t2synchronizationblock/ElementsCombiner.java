package pl.mentoring.t2synchronizationblock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElementsCombiner extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ElementsCombiner.class);

    private final List<Integer> collection;

    private final AtomicBoolean keepOnRunning;

    public ElementsCombiner(List<Integer> collection, AtomicBoolean keepOnRunning) {
        this.collection = collection;
        this.keepOnRunning = keepOnRunning;
    }

    @Override
    public void run() {
        while (keepOnRunning.get()) {

            try {
                double sumOfSquares = 0L;
                synchronized (collection) {
                    for (Integer element : collection) {
                        sumOfSquares += element * element;
                    }
                    logger.info("Square root of sum of squares of all numbers in the collection = {}. Number of elements = {}",
                        Math.sqrt(sumOfSquares), collection.size());
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
