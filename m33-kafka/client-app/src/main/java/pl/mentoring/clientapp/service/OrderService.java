package pl.mentoring.clientapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mentoring.clientapp.exception.EntityNotFoundException;
import pl.mentoring.clientapp.model.Order;
import pl.mentoring.clientapp.model.OrderItem;
import pl.mentoring.clientapp.model.OrderStatus;
import pl.mentoring.clientapp.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + "not found"));
    }

    public Order saveNewOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.setItems(orderItems);
        order.setStatus(OrderStatus.NEW);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrder(orderId);

        order.setStatus(newStatus);

        return orderRepository.save(order);
    }
}
