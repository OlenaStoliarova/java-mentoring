package pl.mentoring.t1threadsafemap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        AtomicBoolean keepOnRunning = new AtomicBoolean(true);

        logger.info("Testing regular HashMap...");
        Map<Integer, Integer> regularHashMap = new HashMap<>();
        Thread elementProducer = new ElementProducer(regularHashMap, keepOnRunning);
        Thread valuesAdder = new ValuesSumator(regularHashMap, keepOnRunning);

        long runTime = runBoth(elementProducer, valuesAdder);
        logger.info("Regular HashMap run into ConcurrentModificationException with two threads in {} ms", runTime);

        logger.info("Testing ConcurrentHashMap...");
        keepOnRunning.set(true);
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Thread elementProducer2 = new ElementProducer(concurrentHashMap, keepOnRunning);
        Thread valuesAdder2 = new ValuesSumator(concurrentHashMap, keepOnRunning);

        runTime = runBoth(elementProducer2, valuesAdder2);
        logger.info("ConcurrentHashMap worked with two threads for {} ms", runTime);

        logger.info("Testing Collections.synchronizedMap()...");
        keepOnRunning.set(true);
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(map);
        Thread elementProducer3 = new ElementProducer(synchronizedMap, keepOnRunning);
        Thread valuesAdder3 = new ValuesSumator(synchronizedMap, keepOnRunning);

        runTime = runBoth(elementProducer3, valuesAdder3);
        logger.info("Collections.synchronizedMap() run into ConcurrentModificationException with two threads in {} ms", runTime);
    }

    private static long runBoth(Thread elementProducer, Thread valuesAdder) throws InterruptedException {
        long start = System.currentTimeMillis();
        elementProducer.start();
        valuesAdder.start();

        elementProducer.join();
        valuesAdder.join();
        return System.currentTimeMillis() - start;
    }
}
