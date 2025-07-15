package com.backend.api.v1.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.CascadeType.REMOVE;

@NoArgsConstructor
@Getter
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int orderId;

    private String email;
    private LocalDateTime order_date;
    private int totalPrice;
    private boolean orderStatus;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> OrderItems = new ArrayList<>();
}
