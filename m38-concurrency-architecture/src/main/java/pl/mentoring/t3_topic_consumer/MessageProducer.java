package pl.mentoring.t3_topic_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class MessageProducer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private final String name;

    private final Random rand;

    public MessageProducer(String name) {
        this.name = name;

        Random rand1;
        try {
            rand1 = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Exception getting Random", e);
            rand1 = new Random();
        }
        this.rand = rand1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                int index = rand.nextInt(Topic.values().length);
                String generatedText = name + " " + i;

                Message message = new Message(Topic.valueByIndex(index), generatedText);
                logger.info("Thread {}. Producer {} is posting message {}", Thread.currentThread().getId(), name, message);
                AsynchronousMessageBus.getInstance().postMessage(message);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
