package pl.mentoring.t3_topic_consumer;

public enum Topic {
    INFO(0),
    DEBUG(1),
    ERROR(2);

    private final int value;

    Topic(int value) {
        this.value = value;
    }

    public static Topic valueByIndex(int index) {
        for (Topic topic : values()) {
            if (topic.value == index) {
                return topic;
            }
        }
        return null;
    }
}
