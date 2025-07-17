package com.backend.api.v1.orders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.CascadeType.REMOVE;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int ordersId;

    private String email;
    private LocalDateTime ordersDate;
    private int totalPrice;
    private boolean orderStatus;
    private String zipCode;
    private String address;

    @OneToMany(mappedBy = "orders", cascade = {PERSIST, REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> ordersItems = new ArrayList<>();

    public Orders(String email, LocalDateTime ordersDate, int totalPrice, boolean orderStatus, String address, String zipCode) {
        this.email = email;
        this.ordersDate = ordersDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.address = address;
        this.zipCode = zipCode;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.ordersItems.add(orderItem);
    }
}
