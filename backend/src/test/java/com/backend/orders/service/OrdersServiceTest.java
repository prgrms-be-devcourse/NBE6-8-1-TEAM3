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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("테스트용 다건 데이터 생성 - orders")
    void createManyOrder() {
        int count = 10;
        List<Orders> orders = testDataFactory.createManyOrders(count);

        assertThat(orders.size()).isEqualTo(count);
        }

    @Test
    @DisplayName("orders 생성시간 비교 테스트")
    void compareOrdersCreateTime() {
        List<Orders> orders = testDataFactory.createManyOrders(10);

        LocalDateTime start = LocalDate.now().minusDays(1).atTime(14, 0);
        LocalDateTime end = LocalDate.now().atTime(14, 0);

        List<Orders> filteredOrders = orders.stream()
                .filter(o -> {
                    LocalDateTime dt = o.getOrdersDate();
                    return (dt.isEqual(start) || dt.isAfter(start)) && dt.isBefore(end);
                })
                .collect(Collectors.toList());

        assertThat(filteredOrders)
                .allSatisfy(order -> {
                    LocalDateTime dt = order.getOrdersDate();
                    assertThat(dt).isAfterOrEqualTo(start);
                    assertThat(dt).isBefore(end);
                });
    }
    }
