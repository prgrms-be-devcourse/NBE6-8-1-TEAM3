package com.backend.api.v1.wishiList.controller;


import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.products.service.ProductsService;
import com.backend.api.v1.wishiList.dto.WishListAddReqDto;
import com.backend.api.v1.wishiList.dto.WishListItemAddReqDto;
import com.backend.api.v1.wishiList.dto.WishListItemDto;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist/")
@Tag(name = "ApiV1WishListController", description = "API 위시리스트 컨트롤러")
public class ApiV1WishListController {
    private final WishListService wishListService;
    private final ProductsService productService;




    @PostMapping
    @Transactional
    @Operation(summary = "위시리스트 생성 및 위시아이템 추가")
    public int createOrUpdateWishList(
            @RequestBody WishListAddReqDto reqBody) {
        WishList wishlist;
        //위시리스트가 없으면 생성
        if(wishListService.findByEmail(reqBody.email()).isEmpty()){

            //매개값 다받기 ver
            //wishlist = wishListService.createWishList(
                    //reqBody.email(), reqBody.address(), reqBody.zipCode(), reqBody.totalQuantity(), reqBody.totalPrice());
            //매개값 객체로 받기 ver
            wishlist = wishListService.createWishList(reqBody);
        }
        else{
            //위시리스트가 있으면 불러오기
            wishlist = wishListService.findByEmail(reqBody.email()).get();
        }

        //위시 리스트 정보 업데이트 매개값 다받기 ver
        //wishListService.modify(wishlist, reqBody.email(), reqBody.address(), reqBody.zipCode(), reqBody.totalQuantity(), reqBody.totalPrice());
        //위시 리스트 정보 업데이트 객체로 전송
        wishListService.modify(wishlist, reqBody);

        //위시 리스트 처리 후 아이템 생성
        for(WishListItemAddReqDto itemDto : reqBody.items()) {
            Products product = productService.findById(itemDto.id()).get();
            wishListService.addItem(wishlist, product, itemDto.quantity());
        }
        //추가 후 위시리스트아이디만 리턴
        return wishlist.getWishId();
    }


    @DeleteMapping
    @Transactional
    @Operation(summary = "위시리스트 삭제")
    public void deleteWishList(
            @RequestParam int wishId) {
        WishList wishlist = wishListService.findById(wishId)
                .orElseThrow(() -> new EntityNotFoundException("위시리스트를 찾을 수 없습니다."));
        wishListService.deleteWishList(wishlist);
    }

    @PutMapping("/{wishId}")
    @Transactional
    @Operation(summary = "위시리스트 및 아이템 수정")
    public int updateWishList(
            @PathVariable int wishId,
            @RequestBody WishListAddReqDto reqBody) {
        //변경 위시리스트 조회
        WishList wishlist = wishListService.findById(wishId)
                .orElseThrow(() -> new EntityNotFoundException("위시리스트를 찾을 수 없습니다."));

        //위시리스트 정보 업데이트 매개값 다받기 ver
        //wishListService.modify(wishlist,
                //reqBody.email(), reqBody.address(),reqBody.zipCode(), reqBody.totalQuantity(), reqBody.totalPrice());
        //객체로 받기 ver
        wishListService.modify(wishlist, reqBody);

        //위시 리스트 아이템 업데이트
        // 1. 기존 아이템 전부 삭제
        wishListService.deleteAllItems(wishlist);
        // 2. 새 아이템들 추가
        for (WishListItemAddReqDto itemDto : reqBody.items()) {
            Products product = productService.findById(itemDto.id()).
                    orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
            wishListService.addItem(wishlist, product, itemDto.quantity());
        }
        return wishlist.getWishId();
    }

    @GetMapping("/{wishId}")
    @Operation(summary = "위시리스트의 아이템 조회")
    public List<WishListItemDto> getWishListItems(
            @PathVariable int wishId
    ){  //위시리스트 조회
        WishList wishlist = wishListService.findById(wishId)
                .orElseThrow(() -> new EntityNotFoundException("위시리스트를 찾을 수 없습니다."));

        //위시리스트 아이템 리스트 반환
        return wishlist
                .getWishListItem()
                .stream()
                .map(WishListItemDto::new)
                .toList();
    }

}
