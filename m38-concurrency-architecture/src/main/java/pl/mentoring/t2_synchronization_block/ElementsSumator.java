package pl.mentoring.t2_synchronization_block;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElementsSumator extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ElementsSumator.class);

    private final List<Integer> collection;

    private final AtomicBoolean keepOnRunning;

    public ElementsSumator(List<Integer> collection, AtomicBoolean keepOnRunning) {
        this.collection = collection;
        this.keepOnRunning = keepOnRunning;
    }

    @Override
    public void run() {
        while (keepOnRunning.get()) {
            try {
                Long sum = 0L;
                synchronized (collection) {
                    for (Integer element : collection) {
                        sum += element;
                    }
                    logger.info("Sum of the numbers in the collection = {}. Number of elements = {}", sum, collection.size());
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
