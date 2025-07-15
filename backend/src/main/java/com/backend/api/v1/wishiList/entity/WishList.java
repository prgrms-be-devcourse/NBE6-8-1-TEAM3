package com.backend.api.v1.wishiList.entity;

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
public class WishList {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int wishId;

    @OneToMany(mappedBy = "wishList", cascade = {PERSIST, REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WishListItem> wishListItem = new ArrayList<>();

    private String email;
    private String address;
    private int totalCount;
    private int totalPrice;
}