package com.backend.api.v1.wishiList.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min= 5, max = 100)
    private String email;
    @NotBlank
    @Size(min = 4, max = 100)
    private String address;
    @NotBlank
    @Size(min = 5, max = 5)
    private String zipCode;
    @NotBlank
    private int totalCount;
    @NotBlank
    private int totalPrice;

    public WishList(String email, String address,String zipCode, int totalCount, int totalPrice) {
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
    }

    public void modify(String email, String address, String zipCode, int totalCount, int totalPrice) {
        this.email = email;
        this.address = address;
        this.totalCount = totalCount;
        this.zipCode = zipCode;
        this.totalPrice = totalPrice;
    }



}