package com.backend.api.v1.orders.service;

import com.backend.api.v1.orders.entity.OrderItem;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderItemRepository;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.wishiList.entity.WishListItem;
import com.backend.api.v1.wishiList.entity.WishList;
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
    //전날 오후 2시 ~ 금일 오후 2시 사이 미배송 객체 리턴
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public List<Orders> getUndeliveredOrders() {
        List<Orders> orderList = orderRepository.findAll();

        LocalDateTime start = LocalDate.now().minusDays(1).atTime(14, 0);
        LocalDateTime end = LocalDate.now().atTime(14, 0);

        return orderList.stream()
                .filter(o -> !o.isOrderStatus())
                .filter(o -> {
                    LocalDateTime dt = o.getOrdersDate();
                    return (dt.isEqual(start) || dt.isAfter(start)) && dt.isBefore(end);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Orders> deliverOrder() {
        //전날 오후 2시 ~ 금일 오후 2시 사이 미배송 객체 배송 시작
        List<Orders> undeliveredOrders = getUndeliveredOrders();
        List<Orders> deliveredOrders = new ArrayList<>();

        for(Orders order : undeliveredOrders){
            order.setOrderStatus(true);
            deliveredOrders.add(order);
        }

        return deliveredOrders;
    }

    public List<Orders> getDeliveredOrders() {
        //전날 오후 2시 ~ 금일 오후 2시 사이 배송 객체 리턴
        List<Orders> orderList = orderRepository.findAll();

        LocalDateTime start = LocalDate.now().minusDays(1).atTime(14, 0);
        LocalDateTime end = LocalDate.now().atTime(14, 0);

        return orderList.stream()
                .filter(Orders::isOrderStatus)
                .filter(o -> {
                    LocalDateTime dt = o.getOrdersDate();
                    return (dt.isEqual(start) || dt.isAfter(start)) && dt.isBefore(end);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public String createOrders(WishList wishList) {
        String email = wishList.getEmail();
        String address = wishList.getAddress();
        LocalDateTime ordersDate = LocalDateTime.now();
        int totalPrice = wishList.getTotalPrice();
        boolean orderStatus = false;
        String zipCode = wishList.getZipCode();

        Orders order = new Orders(email, ordersDate, totalPrice, orderStatus, address, zipCode);

        for (WishListItem item : wishList.getWishListItem()) {
            Products products = item.getProducts();
            int totalCount = item.getCount();

            OrderItem orderItem = new OrderItem(order, products, totalCount);

            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return "주문 완료되었습니다.";
    }
}