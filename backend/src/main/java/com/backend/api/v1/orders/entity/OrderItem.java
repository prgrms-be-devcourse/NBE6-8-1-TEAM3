package com.backend.api.v1.orders.entity;

import com.backend.api.v1.products.entity.Products;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Products products;

    private int quantity;

    public OrderItem(Orders orders, Products products, int quantity) {
        this.orders = orders;
        this.products = products;
        this.quantity = quantity;
    }
}
