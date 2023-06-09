package pl.mentoring.palmetto.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.mentoring.palmetto.configuration.KafkaProducerConfig;
import pl.mentoring.palmetto.model.Order;
import pl.mentoring.palmetto.model.OrderStatus;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationService.class);

    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final OrderService orderService;

    public void sendMessage(Long newOrderId) {
        kafkaTemplate.send(KafkaProducerConfig.ORDER_TOPIC, newOrderId);
    }

    @KafkaListener(topics = "notifications", concurrency = "3")
    public void listenToNotificationsAndUpdateOrderStatus(ConsumerRecord<Long, String> newNotification) {

        logger.info("Received Message {} for key {}", newNotification.value(), newNotification.key());

        Order updatedOrder = orderService.updateOrderStatus(newNotification.key(), OrderStatus.valueOf(newNotification.value()));

        logger.info("Order {} status updated to {}", updatedOrder.getId(), updatedOrder.getStatus());
    }

}
