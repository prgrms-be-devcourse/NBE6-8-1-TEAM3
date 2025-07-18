package com.backend.api.v1.wishiList.dto;

import com.backend.api.v1.wishiList.entity.WishList;

import java.util.List;

public record WishListDto(

        String email,
        String address,
        String zipCode,
        int totalCount,
        int totalPrice,
        List<WishListItemDto> wishListItems
) {
    public WishListDto(WishList wishlist){
        this(
                wishlist.getEmail(),
                wishlist.getAddress(),
                wishlist.getZipCode(),
                wishlist.getTotalCount(),
                wishlist.getTotalPrice()
                , wishlist.getWishListItem().stream()
                        .map(WishListItemDto::new)
                        .toList()
        );
    }

}
