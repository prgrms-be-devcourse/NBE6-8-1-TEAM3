package com.backend.api.v1.wishListItem.repository;

import com.backend.api.v1.wishListItem.entity.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListItem, Integer> {
}
