package com.backend.api.v1.wishiList.controller;


import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.products.service.ProductsService;
import com.backend.api.v1.wishiList.dto.WishListAddReqDto;
import com.backend.api.v1.wishiList.dto.WishListDto;
import com.backend.api.v1.wishiList.dto.WishListItemAddReqDto;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
@Tag(name = "WishListController", description = "API 위시리스트 컨트롤러")
public class WishListController {
    private final WishListService wishListService;
    private final ProductsService productService;




    @PostMapping
    @Transactional
    @Operation(summary = "위시리스트 생성 및 위시아이템 추가")
    public int createWishList(
            @RequestBody WishListAddReqDto reqBody) {
        //위시 리스트 생성
        WishList wishlist = wishListService.createWishList(reqBody);

        //위시 아이템 생성
        for(WishListItemAddReqDto itemDto : reqBody.items()) {
            Products product = productService.findById(itemDto.id()).get();
            wishListService.addItem(wishlist, product, itemDto.quantity());
        }
        //추가 후 위시리스트 아이디만 리턴
        return wishlist.getWishId();
    }


    @DeleteMapping
    @Transactional
    @Operation(summary = "위시리스트 삭제")
    public void deleteWishList(
            @RequestParam(name = "wishListId") int wishId) {
        WishList wishlist = wishListService.findById(wishId)
                .orElseThrow(() -> new EntityNotFoundException("위시리스트를 찾을 수 없습니다."));
        wishListService.deleteWishList(wishlist);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "위시리스트 및 아이템 수정")
    public int updateWishList(
            @RequestParam(name = "wishListId") int wishId,
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

    @GetMapping
    @Operation(summary = "위시리스트 조회")
    public WishListDto getWishListItems(
            @RequestParam(name = "wishListId") int wishId
    ){  //위시리스트 조회
        WishList wishlist = wishListService.findById(wishId)
                .orElseThrow(() -> new EntityNotFoundException("위시리스트를 찾을 수 없습니다."));

        //WishListDto로 변환하여 반환
        return new WishListDto(wishlist);
    }
}
