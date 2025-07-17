package com.backend.api.v1.wishiList.controller;


import com.backend.api.v1.wishiList.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{wishListId}/wishListItems")
public class ApiV1WishListItemController {
    private final WishListService wishListService;

}
