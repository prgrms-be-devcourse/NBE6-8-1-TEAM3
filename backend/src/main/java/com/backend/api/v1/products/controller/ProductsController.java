package com.backend.api.v1.products.controller;

import com.backend.api.v1.products.dto.ProductDto;
import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.products.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "상품 목록 조회")
    public List<ProductDto> getProducts() {
        List<Products> items = productsService.findAll();

        return items
                .stream()
                .map(ProductDto::new) // ProductDto 전환
                .collect(Collectors.toList());
    }
}
