package pl.mentoring.t3_topic_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TopicMessageConsumer.class);

    private final String name;

    private final Topic topic;

    public TopicMessageConsumer(String name, Topic topic) {
        this.name = name;
        this.topic = topic;
        AsynchronousMessageBus.getInstance().registerSubscriber(this);
    }

    public void consumeMessage(Message currentMessage) {
        if (currentMessage.getTopic() == topic) {
            logger.info("Thread {}. Consumer {} on topic {} have read message {}",
                Thread.currentThread().getId(), name, topic, currentMessage);
        }
    }
}
