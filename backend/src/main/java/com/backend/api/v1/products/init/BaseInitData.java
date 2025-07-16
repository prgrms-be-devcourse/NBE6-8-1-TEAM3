package com.backend.api.v1.products.init;

import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final ProductsRepository productsRepository;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (productsRepository.count() > 0) return;

        productsRepository.save(new Products("아이스아메리카노", "아아", 4500, "images/아이스아메리카노"));
        productsRepository.save(new Products("카페라떼", "카페라떼", 5000, "images/카페라떼"));
        productsRepository.save(new Products("녹차라떼", "녹차라떼", 5300, "images/녹차라떼"));
        productsRepository.save(new Products("치즈케이크", "치즈케이크", 6000, "images/치즈케이크"));
    }
}