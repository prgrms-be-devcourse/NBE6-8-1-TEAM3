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

    @Column(unique = true) //한 이메일 당 하나의 위시리스트만 존재
    private String email;

    private String address;
    private String zipCode;
    private int totalCount;
    private int totalPrice;

    public WishList(String email, String address,String zipCode, int totalCount, int totalPrice) {
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
    }

    public void modify(String email, String address, int totalCount, int totalPrice) {
        this.email = email;
        this.address = address;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
    }



}