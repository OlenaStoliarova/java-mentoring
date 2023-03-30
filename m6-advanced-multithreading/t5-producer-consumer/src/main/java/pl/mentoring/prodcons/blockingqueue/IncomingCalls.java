package pl.mentoring.prodcons.blockingqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class IncomingCalls extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(IncomingCalls.class);

    private final BlockingQueue<String> waitingList;
    private final AtomicBoolean operationalHours;

    public IncomingCalls(BlockingQueue<String> waitingList, AtomicBoolean operationalHours) {
        this.waitingList = waitingList;
        this.operationalHours = operationalHours;
    }

    @Override
    public void run() {
        int i = 1;
        while (operationalHours.get()) {
            try {
                waitingList.add("Incoming call #" + i);
                logger.info("Incoming call #{} - remaining capacity of waiting list: {}", i, waitingList.remainingCapacity());
                Thread.sleep(200); // time to next Incoming call
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        logger.info("End of the operational hours. Please call back tomorrow!");
    }
}
