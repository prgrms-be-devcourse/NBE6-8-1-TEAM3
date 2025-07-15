package com.backend.api.v1.orders.global;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        Orders order1 = new Orders("qkek6223", LocalDateTime.now(),
                10000, false);
//        Orders order2 = new Orders();
        orderRepository.save(order1);
//        orderRepository.save(order2);
    }
}

//@Id
//    @GeneratedValue(strategy = IDENTITY)
//    private int ordersId;
//
//    private String email;
//    private LocalDateTime ordersDate;
//    private int totalPrice;
//    private boolean ordersStatus;
//
//    @OneToMany(mappedBy = "orders", cascade = {PERSIST, REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<OrderItem> ordersItems = new ArrayList<>();
