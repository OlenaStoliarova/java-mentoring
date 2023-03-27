package pl.mentoring.prodcons.semaphore;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProducerConsumerOnSemaphoresDemo {

    private static final int QSIZE = 20;

    private static Queue<String> queue = new ConcurrentLinkedQueue<>();

    private static Semaphore modificationKey = new Semaphore(1);
    private static Semaphore fillCount = new Semaphore(0);
    private static Semaphore emptyCount = new Semaphore(QSIZE);

    private static AtomicBoolean operationalHours = new AtomicBoolean(true);

    public static void main(String[] args) {
        new CallCenterOperator("1").start();
        new CallCenterOperator("2").start();
        new IncomingCallsProducer().start();

        try {
            Thread.sleep(10000); //wait end of business day
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        operationalHours.set(false);
    }

    private static class CallCenterOperator extends Thread {
        public CallCenterOperator(String s) {
            super(s);
        }

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
                }
                try {
                    System.out.format("Operator %s took %s\n", getName(), call);
                    Thread.sleep(500); // time to answer a call
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.format("End of the business day for operator %s. WooHoo!\n", getName());
        }
    }

    private static class IncomingCallsProducer extends Thread {
        public void run() {
            int i = 1;
            while (operationalHours.get()) {
                try {
                    emptyCount.acquire();
                    modificationKey.acquire();

                    queue.add("Incoming call #" + i);

                    fillCount.release();
                    modificationKey.release();

                    System.out.format("Incoming call #%d added to queue\n", i);
                    Thread.sleep(100); // time to next Incoming call
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("End of the operational hours. Please call back tomorrow!");
        }
    }
}
