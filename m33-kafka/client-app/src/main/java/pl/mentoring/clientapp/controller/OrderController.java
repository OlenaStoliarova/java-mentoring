package pl.mentoring.clientapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mentoring.clientapp.model.Order;
import pl.mentoring.clientapp.model.OrderItem;
import pl.mentoring.clientapp.service.CommunicationService;
import pl.mentoring.clientapp.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CommunicationService communicationService;

    @PostMapping
    public Order newOrder(@RequestBody List<OrderItem> orderItems) {
        Order savedOrder = orderService.saveNewOrder(orderItems);
        communicationService.sendMessage(savedOrder.getId());
        return savedOrder;
    }

    @GetMapping(value = "/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

}
