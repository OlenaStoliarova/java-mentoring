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
public class KitchenService {
    private static final Logger logger = LoggerFactory.getLogger(KitchenService.class);

    public static final String ORDER_TOPIC = "orders";
    public static final String NOTIFICATIONS_TOPIC = "notifications";

    private KafkaTemplate<Long, String> kafkaTemplate;

    @KafkaListener(topics = ORDER_TOPIC)
    public void processOrder(ConsumerRecord<String, Long> newOrder) {
        Long newOrderId = newOrder.value();
        logger.info("Palmetto kitchen got order {}", newOrderId);

        kafkaTemplate.send(NOTIFICATIONS_TOPIC, newOrderId, OrderStatus.COOKING.name());

        emulateCookingTime(10000);

        kafkaTemplate.send(NOTIFICATIONS_TOPIC, newOrderId, OrderStatus.READY_FOR_DELIVERY.name());

        logger.info("Palmetto kitchen finished cooking order {}", newOrderId);
    }

    private void emulateCookingTime(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
