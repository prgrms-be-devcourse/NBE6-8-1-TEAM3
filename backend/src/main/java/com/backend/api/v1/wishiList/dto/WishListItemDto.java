package com.backend.api.v1.wishiList.dto;

import com.backend.api.v1.products.dto.ProductDto;
import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.wishiList.entity.WishListItem;

public record WishListItemDto(
        ProductDto product,
        int quantity
) {

    public WishListItemDto(WishListItem wishItem){
        this(
                new ProductDto(
                        wishItem.getProducts().getProductId(),
                        wishItem.getProducts().getProductName(),
                        wishItem.getProducts().getProductDescription(),
                        wishItem.getProducts().getProductPrice(),
                        wishItem.getProducts().getPath()
                ),
                wishItem.getCount()
        );
    }
}
