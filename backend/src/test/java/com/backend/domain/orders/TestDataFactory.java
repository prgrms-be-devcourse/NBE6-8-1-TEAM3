package com.backend.domain.orders;

import com.backend.api.v1.orders.entity.OrderItem;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderItemRepository;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.products.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        String address = "샘플주소";
        String zipCode = "12345";

        Orders order = new Orders(email, ordersDate, totalPrice, orderStatus, address, zipCode);
        return orderRepository.save(order);
    }

    public OrderItem createOrderItem() {
        Orders order = new Orders();
        Products product = new Products();
        int totalCount = 2;

        OrderItem orderItem = new OrderItem(order, product, totalCount);
        return orderItemRepository.save(orderItem);
    }

    public List<Orders> createManyOrders(int count) {
        orderRepository.deleteAll();

        for (int i = 0; i < count; i++) {
            String email = "test@example.com";
            LocalDateTime now = LocalDateTime.now();

            int randomHour = ThreadLocalRandom.current().nextInt(0, 24);
            int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);
            int randomSecond = ThreadLocalRandom.current().nextInt(0, 60);

            LocalDateTime ordersDate = now.withHour(randomHour).withMinute(randomMinute).withSecond(randomSecond);

            int totalPrice = 15000;
            boolean orderStatus = false;
            String address = "샘플주소";
            String zipCode = "12345";

            Orders order = new Orders(email, ordersDate, totalPrice, orderStatus, address, zipCode);
            orderRepository.save(order);
        }
        return orderRepository.findAll();
    }
}
