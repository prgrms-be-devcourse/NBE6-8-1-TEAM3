package com.backend.api.v1.orders.service;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderRepository orderRepository;

    public List<Orders> getUndeliveredOrders() {
        List<Orders> orderList = orderRepository.findAll();

        LocalDateTime start = LocalDate.now().minusDays(1).atTime(14, 0);
        LocalDateTime end = LocalDate.now().atTime(14, 0);

        List<Orders> filteredOrders = orderList.stream()
                .filter(o -> !o.isOrderStatus())
                .filter(o -> {
                    LocalDateTime dt = o.getOrdersDate();
                    return (dt.isEqual(start) || dt.isAfter(start)) && dt.isBefore(end);
                })
                .collect(Collectors.toList());

        return filteredOrders;
    }

    @Transactional
    public List<Orders> deliverOrder() {
        List<Orders> undeliveredOrders = getUndeliveredOrders();
        List<Orders> deliveredOrders = new ArrayList<>();

        for(Orders order : undeliveredOrders){
            order.setOrderStatus(true);
            deliveredOrders.add(order);
        }

        return deliveredOrders;
    }
}
