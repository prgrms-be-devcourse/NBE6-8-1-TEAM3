package com.backend.api.v1.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto {
    @NotNull
    private int wishListId;
}
