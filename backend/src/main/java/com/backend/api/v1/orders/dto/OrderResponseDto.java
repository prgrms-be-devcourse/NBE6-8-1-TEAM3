package com.backend.api.v1.orders.dto;

import com.backend.api.v1.orders.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private int ordersId;
    private String email;
    private LocalDateTime ordersDate;
    private int totalPrice;
    private boolean orderStatus;
    private List<OrderItemDto> orderItems;
}
