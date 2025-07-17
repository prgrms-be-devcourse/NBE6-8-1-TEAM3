package com.backend.api.v1.wishiList.dto;

import com.backend.api.v1.wishiList.entity.WishList;

public record WishListDto(
        int id,
        String email,
        String address,
        int totalCount,
        int totalPrice
) {
    public WishListDto(WishList wishlist){
        this(
                wishlist.getWishId(),
                wishlist.getEmail(),
                wishlist.getAddress(),
                wishlist.getTotalCount(),
                wishlist.getTotalPrice()
        );
    }

}
