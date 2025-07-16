package com.backend.api.v1.orders.controller;

import com.backend.api.v1.orders.dto.OrderDto;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.service.OrdersService;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrdersService ordersService;
    private final WishListRepository wishListRepository;

    @PostMapping("")
    public String createOrder(@RequestBody OrderDto request) {
        int wishListId = request.getWishListId();
        Optional<WishList> wishList = wishListRepository.findById(wishListId);

        if (wishList.isPresent()) {
            return ordersService.createOrders(wishList.get());
        } else {
            return "해당 id의 withList가 존재하지 않음";
        }
    }

    @GetMapping("/shipping")
    public List<Orders> orders() {
        // 현재 시간 무시하고 전날 오후 2시~금일 오후 2시 사이 미배송 객체들을 배송 시작
         ordersService.deliverOrder();

        //배송되고 있는 주문목록만 보여주는 페이지
        return ordersService.getDeliveredOrders();
    }
}
