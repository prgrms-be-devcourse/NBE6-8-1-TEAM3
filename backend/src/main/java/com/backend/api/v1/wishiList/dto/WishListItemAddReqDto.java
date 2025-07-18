package com.backend.api.v1.wishiList.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record WishListItemAddReqDto(
        @NotBlank
        int id, //
        // 실제 Products 엔티티와 ManyToOne 관계를 끊고 ID만 저장하기 위함.
        @Min(value = 1)
        int quantity
) {}
