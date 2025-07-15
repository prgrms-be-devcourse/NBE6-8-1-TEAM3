package com.backend.api.v1.orders.service;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderRepository orderRepository;

    public void delivery() {
        //레포지토리에서 동일한 이메일을 가진 것들을 가져온다?

        List<Orders> orderList = orderRepository.findAll();

        if (orderList.isEmpty()) {
            System.out.println("==> orderList is empty!");
        } else {
            System.out.println(orderList.toString());
        }
        //근데 그 중에서 전날 오후 2시~금일 오후 2시면서
        // false인 것들만 가져오기.

        //배송 현황 페이지를 하나 만들까?
        //그러려면 전체를 보여줘야 함.
    }
}
