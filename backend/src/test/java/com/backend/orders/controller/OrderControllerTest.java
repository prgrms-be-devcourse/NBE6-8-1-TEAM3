package com.backend.orders.controller;

import com.backend.api.v1.orders.controller.OrderController;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.service.OrdersService;
import com.backend.orders.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderControllerTest {

    @Autowired
    private TestDataFactory testDataFactory;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderController orderController;

    @Test
    @DisplayName("/orders")
    void getOrders() {
        testDataFactory.createManyOrders(10);
        List<Orders> deliveredOrders = ordersService.deliverOrder();

        assertThat(deliveredOrders.size()).isEqualTo(orderController.orders().size());
    }
}
