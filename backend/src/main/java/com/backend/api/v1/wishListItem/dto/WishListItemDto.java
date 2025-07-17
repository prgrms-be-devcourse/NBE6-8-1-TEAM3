package com.backend.api.v1.wishListItem.dto;

import com.backend.api.v1.wishListItem.entity.WishListItem;

public record WishListItemDto(
        int productId,
        int quantity
) {

    public WishListItemDto(WishListItem wishItem){
        this(
                wishItem.getProducts().getProductId(),
                wishItem.getCount()
        );
    }
}
