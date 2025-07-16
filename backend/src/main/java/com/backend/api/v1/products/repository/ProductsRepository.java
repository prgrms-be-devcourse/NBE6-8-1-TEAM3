package com.backend.api.v1.products.repository;

import com.backend.api.v1.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
