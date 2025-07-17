package com.backend.api.v1.wishiList.repository;

import com.backend.api.v1.wishiList.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    Optional<WishList> findByEmail(String email);
}
