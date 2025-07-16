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
@RequestMapping("/api/v1")
public class OrderController {

    private final OrdersService ordersService;


    @GetMapping("/orders")
    public List<Orders> orders() {
        ordersService.deliverOrder();

        //배송되고 있는 주문목록만 보여주는 페이지
        return ordersService.getDeliveredOrders();
    }
}
