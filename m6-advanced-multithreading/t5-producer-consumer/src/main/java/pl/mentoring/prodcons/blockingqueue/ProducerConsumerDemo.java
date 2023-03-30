package pl.mentoring.prodcons.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProducerConsumerDemo {

    public static void main(String[] args) {
        BlockingQueue<String> waitingList = new ArrayBlockingQueue<>(20);
        AtomicBoolean operationalHours = new AtomicBoolean(true);

        CallCenterOperator operator1 = new CallCenterOperator(waitingList, operationalHours);
        operator1.setName("1");
        operator1.start();

        CallCenterOperator operator2 = new CallCenterOperator(waitingList, operationalHours);
        operator2.setName("2");
        operator2.start();

        new IncomingCalls(waitingList, operationalHours).start();

        try {
            Thread.sleep(10000); //wait end of business day
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        operationalHours.set(false);
    }
}
