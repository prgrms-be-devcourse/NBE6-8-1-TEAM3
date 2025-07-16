package com.backend.api.v1.products.service;

import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public List<Products> findAll() {
        return productsRepository.findAll();
    }
}
