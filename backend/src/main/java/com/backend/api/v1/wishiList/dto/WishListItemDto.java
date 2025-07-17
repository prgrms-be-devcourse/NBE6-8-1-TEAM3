package com.backend.api.v1.wishiList.dto;

import com.backend.api.v1.wishiList.entity.WishListItem;

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
