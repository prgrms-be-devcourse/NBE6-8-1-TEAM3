package com.backend.api.v1.wishiList.service;


import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.wishiList.dto.WishListAddReqDto;
import com.backend.api.v1.wishiList.entity.WishListItem;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;

    @EntityGraph(attributePaths = {"wishListItem"})
    public Optional<WishList>findById(int wishId) {
        return wishListRepository.findById(wishId);
    }
    public Optional<WishList> findByEmail(String userEmail) {
        return wishListRepository.findByEmail(userEmail);
    }

    // 위시리스트 생성 (매개값 다받기 ver)
    public WishList createWishList(String email, String address,String zipCode, int totalCount, int totalPrice) {

        WishList wishList = new WishList(email, address,zipCode, totalCount, totalPrice);
        return wishListRepository.save(wishList);
    }
    //위시리스트 생성 (매개값 객체로 받기 ver)
    public WishList createWishList(WishListAddReqDto wishDto) {
        WishList wishList = new WishList(
                wishDto.email(),
                wishDto.address(),
                wishDto.zipCode(),
                wishDto.totalQuantity(),
                wishDto.totalPrice()
        );
        wishListRepository.save(wishList);
        return wishList;
    }

    //위시 리스트 수정 매개값 다받 ver
    public void modify(WishList wishlist, String email, String address, String zipCode, int totalCount, int totalPrice) {
        wishlist.modify(email, address, zipCode, totalCount, totalPrice);
    }
    //위시 리스트 수정 매개값 객체로 받기 ver
    public void modify(WishList wishlist, WishListAddReqDto wishDto) {
        wishlist.modify(
                wishDto.email(),
                wishDto.address(),
                wishDto.zipCode(),
                wishDto.totalQuantity(),
                wishDto.totalPrice()
        );
    }
    //위시 리스트 삭제
    public void deleteWishList(WishList wishlist) {
        wishListRepository.delete(wishlist);
    }


    //위시 리스트 아이템 추가
    public void addItem(WishList wishlist, Products product, int count) {
        WishListItem wishListItem = new WishListItem(wishlist, product, count);
        wishlist.getWishListItem().add(wishListItem);
    }

    //위시 리스트 아이템 단건 삭제
    public void deleteItem(WishList wishlist, WishListItem wishListItem) {
        //orphanRemoval로 인해 위시리스트 아이템이 삭제되면 자동으로 해당 엔티티도 삭제
        wishlist.getWishListItem().remove(wishListItem);
        wishListRepository.save(wishlist);
    }

    //위시 리스트 아이템 전체 삭제
    public void deleteAllItems(WishList wishlist) {
        wishlist.getWishListItem().clear();
        wishListRepository.save(wishlist);
    }



}
