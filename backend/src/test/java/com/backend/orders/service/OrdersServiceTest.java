package com.backend.orders.service;

import com.backend.api.v1.orders.entity.OrderItem;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderItemRepository;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.orders.service.OrdersService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;

import com.backend.api.v1.products.entity.Products;
import com.backend.orders.TestDataFactory;
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
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private TestDataFactory testDataFactory;


    @Test
    @DisplayName("테스트용 단건 데이터 생성 - orders")
    void createOrder() {
        Orders order = testDataFactory.createOrder();

        Orders findOrder = orderRepository.findById(order.getOrdersId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        assertThat(findOrder.getEmail()).isEqualTo(order.getEmail());
        assertThat(findOrder.getOrdersDate()).isCloseTo(order.getOrdersDate(), within(1, ChronoUnit.SECONDS));
        assertThat(findOrder.getTotalPrice()).isEqualTo(order.getTotalPrice());
        assertThat(findOrder.isOrderStatus()).isEqualTo(order.isOrderStatus());
    }

    @Test
    @DisplayName("테스트용 단건 데이터 생성 - orderItem")
    void createOrderItem() {
        OrderItem orderItem = testDataFactory.createOrderItem();

        OrderItem findOrderItem = orderItemRepository.findById(orderItem.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

        assertThat(findOrderItem.getOrders()).isEqualTo(orderItem.getOrders());
        assertThat(findOrderItem.getProducts()).isEqualTo(orderItem.getProducts());
        assertThat(findOrderItem.getTotalCount()).isEqualTo(orderItem.getTotalCount());
    }
}
