package com.backend.api.v1.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDto {
    private int productId;
    private int quantity;
}