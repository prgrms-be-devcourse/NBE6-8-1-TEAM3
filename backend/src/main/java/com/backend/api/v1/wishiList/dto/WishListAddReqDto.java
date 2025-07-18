package com.backend.api.v1.wishiList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WishListAddReqDto(
        @NotBlank
        @Size(min = 2, max = 100)
        String email,
        @NotBlank
        @Size(min = 2, max = 5000)
        String address,
        @NotBlank
        String zipCode,
        @NotBlank
        int totalQuantity,
        @NotBlank
        int totalPrice,
        @NotBlank
        List<WishListItemAddReqDto> items
) {
}
