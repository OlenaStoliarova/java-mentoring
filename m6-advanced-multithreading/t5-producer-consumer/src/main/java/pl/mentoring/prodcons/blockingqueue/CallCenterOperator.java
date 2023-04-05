package pl.mentoring.prodcons.blockingqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CallCenterOperator extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CallCenterOperator.class);

    private final BlockingQueue<String> waitingList;
    private final AtomicBoolean operationalHours;

    public CallCenterOperator(BlockingQueue<String> waitingList, AtomicBoolean operationalHours) {
        this.waitingList = waitingList;
        this.operationalHours = operationalHours;
    }

    @Override
    public void run() {
        while (operationalHours.get() || (!operationalHours.get() && !waitingList.isEmpty())) {
            try {
                String call = waitingList.take();
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
