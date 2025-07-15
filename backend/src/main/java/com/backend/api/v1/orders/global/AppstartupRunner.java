package com.backend.api.v1.orders.global;

import com.backend.api.v1.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppstartupRunner implements ApplicationRunner {

    private final OrdersService ordersService;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        ordersService.delivery();
    }
}
