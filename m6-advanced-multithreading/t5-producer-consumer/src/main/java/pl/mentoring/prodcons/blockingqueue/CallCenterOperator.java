package pl.mentoring.prodcons.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CallCenterOperator extends Thread {

    private final BlockingQueue<String> waitingList;
    private final AtomicBoolean operationalHours;

    public CallCenterOperator(BlockingQueue<String> waitingList, AtomicBoolean operationalHours) {
        this.waitingList = waitingList;
        this.operationalHours = operationalHours;
    }

    public void run() {
        while (operationalHours.get() || (!operationalHours.get() && !waitingList.isEmpty())) {
            try {
                String call = waitingList.take();
                System.out.format("Operator %s took %s\n", getName(), call);
                Thread.sleep(500); // time to answer a call
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.format("End of the business day for operator %s. WooHoo!\n", getName());
    }
}
