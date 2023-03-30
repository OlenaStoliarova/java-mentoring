package pl.mentoring.prodcons.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProducerConsumerOnSemaphoresDemo {

    private static final Logger logger = LoggerFactory.getLogger(ProducerConsumerOnSemaphoresDemo.class);
    private static final int QSIZE = 20;

    private static final Queue<String> queue = new ConcurrentLinkedQueue<>();

    private static final Semaphore modificationKey = new Semaphore(1);
    private static final Semaphore fillCount = new Semaphore(0);
    private static final Semaphore emptyCount = new Semaphore(QSIZE);

    private static final AtomicBoolean operationalHours = new AtomicBoolean(true);

    public static void main(String[] args) {
        new CallCenterOperator("1").start();
        new CallCenterOperator("2").start();
        new IncomingCallsProducer().start();

        try {
            Thread.sleep(10000); //wait end of business day
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        operationalHours.set(false);
    }

    private static class CallCenterOperator extends Thread {
        public CallCenterOperator(String s) {
            super(s);
        }

        @Override
        public void run() {
            while (operationalHours.get() || (!operationalHours.get() && !queue.isEmpty())) {

                String call = "";
                try {
                    fillCount.acquire();
                    modificationKey.acquire();

                    call = queue.poll();

                    emptyCount.release();
                    modificationKey.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                try {
                    logger.info("Operator {} took {}", getName(), call);
                    Thread.sleep(500); // time to answer a call
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            logger.info("End of the business day for operator {}. WooHoo!", getName());
        }
    }

    private static class IncomingCallsProducer extends Thread {
        @Override
        public void run() {
            int i = 1;
            while (operationalHours.get()) {
                try {
                    emptyCount.acquire();
                    modificationKey.acquire();

                    queue.add("Incoming call #" + i);

                    fillCount.release();
                    modificationKey.release();

                    logger.info("Incoming call #{} added to queue", i);
                    Thread.sleep(100); // time to next Incoming call
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            logger.info("End of the operational hours. Please call back tomorrow!");
        }
    }
}
