package com.backend.api.v1.products.dto;

import com.backend.api.v1.products.entity.Products;

public record ProductDto(
        int id,
        String productName,
        String productDescription,
        int productPrice,
        String path
) {
    public ProductDto(Products products) {
        this(
                products.getProductId(),
                products.getProductName(),
                products.getProductDescription(),
                products.getProductPrice(),
                products.getPath()
        );
    }
}
