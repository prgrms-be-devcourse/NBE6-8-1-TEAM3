package com.backend.api.v1.wishiList.repository;

import com.backend.api.v1.wishiList.entity.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListItem, Integer> {
}
