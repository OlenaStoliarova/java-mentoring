package pl.mentoring.t3_topic_consumer;

public class Message {

    private final Topic topic;

    private final String text;

    public Message(Topic topic, String text) {
        this.topic = topic;
        this.text = text;
    }

    public Topic getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "Message{" +
            "topic=" + topic +
            ", text='" + text + '\'' +
            '}';
    }
}
