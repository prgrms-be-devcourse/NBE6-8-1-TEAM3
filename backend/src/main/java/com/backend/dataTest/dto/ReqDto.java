package com.backend.frontDataTest.DTO;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ReqDto {
    private String email;
    private String address;
    private String zipCode;
    private int totalCount;
    private int totalPrice;
    private List<ItemDto> items;
}
