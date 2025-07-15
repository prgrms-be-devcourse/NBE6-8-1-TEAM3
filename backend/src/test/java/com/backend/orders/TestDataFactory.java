package com.backend.orders;

import com.backend.api.v1.orders.entity.OrderItem;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderItemRepository;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.products.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestDataFactory {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    public Orders createOrder() {
        String email = "test@example.com";
        LocalDateTime ordersDate = LocalDateTime.now();
        int totalPrice = 15000;
        boolean orderStatus = false;

        Orders order = new Orders(email, ordersDate, totalPrice, orderStatus);
        return orderRepository.save(order);
    }

    public OrderItem createOrderItem() {
        Orders order = new Orders();
        Products product = new Products();
        int totalCount = 2;

        OrderItem orderItem = new OrderItem(order, product, totalCount);
        return orderItemRepository.save(orderItem);
    }
}
