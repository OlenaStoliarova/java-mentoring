package pl.mentoring.t3_topic_consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousMessageBus {
    private final List<TopicMessageConsumer> subscriberList;

    private final ExecutorService executorService;

    private static AsynchronousMessageBus instance;

    public static AsynchronousMessageBus getInstance() {
        if (instance == null) {
            instance = new AsynchronousMessageBus();
        }
        return instance;
    }

    private AsynchronousMessageBus() {
        this.subscriberList = new ArrayList<>();

        int processorsCount = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(processorsCount);
    }

    public void postMessage(Message m) {
        for (TopicMessageConsumer subscriber : subscriberList) {
            executorService.submit(() -> subscriber.consumeMessage(m));
        }
    }

    public void registerSubscriber(TopicMessageConsumer subscriber) {
        subscriberList.add(subscriber);
    }

    public void stop() {
        executorService.shutdown();
    }
}
