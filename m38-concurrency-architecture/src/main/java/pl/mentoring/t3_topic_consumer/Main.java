package pl.mentoring.t3_topic_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Example of consuming messages on specific topic");

        Thread producer1 = new MessageProducer("1");
        Thread producer2 = new MessageProducer("2");

        new TopicMessageConsumer("1", Topic.INFO);
        new TopicMessageConsumer("2", Topic.INFO);
        new TopicMessageConsumer("3", Topic.ERROR);
        new TopicMessageConsumer("4", Topic.DEBUG);

        producer1.start();
        producer2.start();

        producer1.join();
        producer2.join();

        AsynchronousMessageBus.getInstance().stop();
    }
}
