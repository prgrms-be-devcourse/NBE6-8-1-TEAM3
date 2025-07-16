package com.backend.api.v1.products.entity;

import com.backend.api.v1.orders.entity.OrderItem;
import com.backend.api.v1.wishiList.entity.WishListItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int productId;

    @OneToMany(mappedBy = "products", cascade = {PERSIST, REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WishListItem> WishListItems = new ArrayList<>();

    @OneToMany(mappedBy = "products", cascade = {PERSIST, REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> OrderItems = new ArrayList<>();

    private String productName;
    private String productDescription;
    private int productPrice;
    private String path;

    public Products(String productName, String productDescription, int productPrice, String path) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.path = path;
    }
}