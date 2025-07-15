package com.backend.orders;

import com.backend.api.v1.orders.entity.OrderItem;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderItemRepository;
import com.backend.api.v1.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataFactory {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    public Orders createOrder() {
        Orders order = new Orders();
        return orderRepository.save(order);
    }

    public OrderItem createOrderItem() {
        OrderItem orderItem = new OrderItem();
        return orderItemRepository.save(orderItem);
    }
}
