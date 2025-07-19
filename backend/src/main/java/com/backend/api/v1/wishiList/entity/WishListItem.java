package com.backend.api.v1.wishiList.entity;

import com.backend.api.v1.products.entity.Products;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class WishListItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int wishListItemId;

    //위시리스트id 연결
    @ManyToOne
    private WishList wishList;

    //상품id 연결
    @ManyToOne(fetch = FetchType.LAZY)
    private Products products;

    @NotNull
    private int count;

    public WishListItem(WishList wishList, Products products, int count) {
        this.wishList = wishList;
        this.products = products;
        this.count = count;
    }
}
