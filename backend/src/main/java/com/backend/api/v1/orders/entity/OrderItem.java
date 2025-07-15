package com.backend.api.v1.orders.entity;

import com.backend.api.v1.products.entity.Products;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Products products;

    private int totalCount;
}
