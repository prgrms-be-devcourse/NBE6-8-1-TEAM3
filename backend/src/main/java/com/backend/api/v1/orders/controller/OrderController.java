package com.backend.api.v1.orders.controller;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrdersService ordersService;

    @GetMapping("/shipping")
    public List<Orders> orders() {
        // 현재 시간 무시하고 전날 오후 2시~금일 오후 2시 사이 미배송 객체들을 배송 시작
        // ordersService.deliverOrder();

        //배송되고 있는 주문목록만 보여주는 페이지
        return ordersService.getDeliveredOrders();
    }
}
