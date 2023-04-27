package pl.mentoring.t1_thread_safe_map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElementProducer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ElementProducer.class);

    private final Map<Integer, Integer> map;
    private final AtomicBoolean keepOnRunning;
    private final Random rand;

    public ElementProducer(Map<Integer, Integer> map, AtomicBoolean keepOnRunning) {
        this.map = map;
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
                map.put(rand.nextInt(10000), rand.nextInt(1000));
                if (map.size() >= 10000) {
                    logger.info("10000 elements successfully added to the map");
                    keepOnRunning.set(false);
                }
            } catch (ConcurrentModificationException e) {
                logger.info("Exception adding element to the map. Current map size - {}", map.size());
                keepOnRunning.set(false);
                throw e;
            }
        }
    }
}
