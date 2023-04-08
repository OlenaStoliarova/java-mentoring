package pl.mentoring.t2synchronizationblock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElementProducer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ElementProducer.class);

    private final List<Integer> collection;

    private final AtomicBoolean keepOnRunning;

    private final Random rand;

    public ElementProducer(List<Integer> collection, AtomicBoolean keepOnRunning) {
        this.collection = collection;
        this.keepOnRunning = keepOnRunning;

        Random rand1;
        try {
            rand1 = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Exception getting Random", e);
            rand1 = new Random();
        }
        this.rand = rand1;
    }

    @Override
    public void run() {
        while (keepOnRunning.get()) {
            try {
                synchronized (collection) {
                    collection.add(rand.nextInt(1000));
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
