package pl.mentoring.t1threadsafemap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ValuesSumator extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ValuesSumator.class);

    private final Map<Integer, Integer> map;
    private final AtomicBoolean keepOnRunning;

    public ValuesSumator(Map<Integer, Integer> map, AtomicBoolean keepOnRunning) {
        this.map = map;
        this.keepOnRunning = keepOnRunning;
    }

    @Override
    public void run() {
        while (keepOnRunning.get()) {
            Long sum = 0L;
            try {
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    sum += entry.getValue();
                }
            } catch (ConcurrentModificationException e) {
                logger.info("Exception during adding all map values. Current map size - {}, total sum {}", map.size(), sum);
                keepOnRunning.set(false);
                throw e;
            }
        }
    }
}
