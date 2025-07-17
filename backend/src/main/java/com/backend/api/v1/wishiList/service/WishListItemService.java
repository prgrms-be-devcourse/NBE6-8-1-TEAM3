package com.backend.api.v1.wishiList.service;

import com.backend.api.v1.wishiList.repository.WishListItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListItemService {
    private final WishListItemRepository wishListItemRepository;


}
