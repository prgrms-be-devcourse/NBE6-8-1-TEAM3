package com.backend.api.v1.orders.scheduler;

import com.backend.api.v1.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersScheduler {

    private final OrdersService ordersService;

    @Scheduled(cron = "0 0 14 * * *")
    public void runSchedule() {
        ordersService.delivery();
    }
}
