package pl.mentoring.palmetto.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.mentoring.palmetto.model.OrderStatus;

@Service
@AllArgsConstructor
public class DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);
    public static final String NOTIFICATIONS_TOPIC = "notifications";

    private KafkaTemplate<Long, String> kafkaTemplate;

    @KafkaListener(topics = NOTIFICATIONS_TOPIC)
    public void deliverReadyOrder(ConsumerRecord<Long, String> newNotification) {

        String orderStatus = newNotification.value();

        if (OrderStatus.READY_FOR_DELIVERY.name().equals(orderStatus)) {
            Long orderId = newNotification.key();
            logger.info("Order {} is in delivery", orderId);

            kafkaTemplate.send(NOTIFICATIONS_TOPIC, orderId, OrderStatus.ON_THE_WAY.name());

            emulateDeliveryTime(20000);

            kafkaTemplate.send(NOTIFICATIONS_TOPIC, orderId, OrderStatus.DELIVERED.name());

            logger.info("Order {} was delivered", orderId);
        }
    }

    private void emulateDeliveryTime(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
