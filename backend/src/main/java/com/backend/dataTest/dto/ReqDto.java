package com.backend.dataTest.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReqDto {
    private String email;
    private String address;
    private String zipCode;
    private int totalQuantity;
    private int totalPrice;
    private List<ItemDto> items;
}