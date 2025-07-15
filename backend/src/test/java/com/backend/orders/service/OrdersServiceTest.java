package com.backend.orders.service;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.orders.service.OrdersService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@Transactional
public class OrdersServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("테스트용 데이터 생성 - orders")
    void createOrder() {
        String email = "test@example.com";
        LocalDateTime ordersDate = LocalDateTime.now();
        int totalPrice = 15000;
        boolean orderStatus = false;

        Orders order = new Orders(email, ordersDate, totalPrice, orderStatus);
        orderRepository.save(order);

        Orders findOrder = orderRepository.findById(order.getOrdersId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        assertThat(findOrder.getEmail()).isEqualTo(email);
        assertThat(findOrder.getOrdersDate()).isCloseTo(ordersDate, within(1, ChronoUnit.SECONDS));
        assertThat(findOrder.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(findOrder.isOrderStatus()).isEqualTo(orderStatus);
    }
}
