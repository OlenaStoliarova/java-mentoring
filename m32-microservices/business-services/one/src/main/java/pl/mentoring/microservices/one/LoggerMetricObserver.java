package pl.mentoring.microservices.one;

import com.netflix.servo.Metric;
import com.netflix.servo.publish.BaseMetricObserver;
import com.netflix.servo.util.Preconditions;
import com.netflix.servo.util.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

public class LoggerMetricObserver extends BaseMetricObserver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LoggerMetricObserver(String name) {
        super(name);
    }

    @Override
    public void updateImpl(List<Metric> metrics) {
        Preconditions.checkNotNull(metrics, "metrics");
        try {
            for (Metric m : metrics) {
                LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(m.getTimestamp()),
                        TimeZone.getDefault().toZoneId());
                logger.info("{}: name[{}] tags[{}] value[{}]", timestamp, m.getConfig().getName(), m.getConfig().getTags(), m.getValue());
            }
        } catch (Exception t) {
            incrementFailedCount();
            throw Throwables.propagate(t);
        }
    }

}
