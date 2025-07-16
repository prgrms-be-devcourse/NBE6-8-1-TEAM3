package com.backend.api.v1.orders.global;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.orders.service.OrdersService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final OrdersService ordersService;
    private final OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 10; i++) {
            String email = "test@example.com";
            LocalDateTime now = LocalDateTime.now();

            int randomHour = ThreadLocalRandom.current().nextInt(0, 24);
            int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);
            int randomSecond = ThreadLocalRandom.current().nextInt(0, 60);

            LocalDateTime ordersDate = now.withHour(randomHour).withMinute(randomMinute).withSecond(randomSecond);

            int totalPrice = 15000;
            boolean orderStatus = false;

            Orders order = new Orders(email, ordersDate, totalPrice, orderStatus);
            orderRepository.save(order);
        }
    }
}
