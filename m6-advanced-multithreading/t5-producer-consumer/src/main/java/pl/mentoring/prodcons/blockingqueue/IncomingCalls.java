package pl.mentoring.prodcons.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class IncomingCalls extends Thread {

    private final BlockingQueue<String> waitingList;
    private final AtomicBoolean operationalHours;

    public IncomingCalls(BlockingQueue<String> waitingList, AtomicBoolean operationalHours) {
        this.waitingList = waitingList;
        this.operationalHours = operationalHours;
    }

    public void run() {
        int i = 1;
        while(operationalHours.get()) {
            try {
                waitingList.add("Incoming call #" + i);
                System.out.format("Incoming call #%d - remaining capacity of waiting list: %d\n", i, waitingList.remainingCapacity());
                Thread.sleep(200); // time to next Incoming call
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("End of the operational hours. Please call back tomorrow!");
    }
}
