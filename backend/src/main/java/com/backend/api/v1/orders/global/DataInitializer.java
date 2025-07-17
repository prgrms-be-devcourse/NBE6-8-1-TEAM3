package com.backend.api.v1.orders.global;

import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.repository.OrderRepository;
import com.backend.api.v1.orders.service.OrdersService;
import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.entity.WishListItem;
import com.backend.api.v1.wishiList.repository.WishListItemRepository;
import com.backend.api.v1.wishiList.repository.WishListRepository;
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
    private final WishListRepository wishListRepository;
    private final WishListItemRepository wishListItemRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 10; i++) {
//            //order 생성기
//            String email = "test@example.com";
//            LocalDateTime now = LocalDateTime.now();
//
//            int randomHour = ThreadLocalRandom.current().nextInt(0, 24);
//            int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);
//            int randomSecond = ThreadLocalRandom.current().nextInt(0, 60);
//
//            LocalDateTime ordersDate = now.withHour(randomHour).withMinute(randomMinute).withSecond(randomSecond);
//
//            int totalPrice = 15000;
//            boolean orderStatus = false;
//            String address = "서울특별시 서초구 반포대로 45, 4층(서초동, 명정빌딩)";
//
//            Orders order = new Orders(email, ordersDate, totalPrice, orderStatus, address);
//            orderRepository.save(order);

            //wishList 생성기
            String email = "test@example.com";
            String address = "서울특별시 서초구 반포대로 45";
            int totalCount = 1;
            int totalPrice = 5000;
            String zipCode = "4층";

            WishList wishList = new WishList(email, address, totalCount, totalPrice, zipCode);
            wishListRepository.save(wishList);

            //wishListItem 생성기
            int itemTotalCount = 2;

            for (int j = 0; j < 3; j++) {
                WishListItem wishListItem = new WishListItem(wishList, itemTotalCount);
                wishListItemRepository.save(wishListItem);
            }
        }
    }
}
