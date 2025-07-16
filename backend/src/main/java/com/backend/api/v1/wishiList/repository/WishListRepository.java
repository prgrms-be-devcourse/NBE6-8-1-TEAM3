package com.backend.api.v1.wishiList.repository;

import com.backend.api.v1.wishiList.entity.WishList;
import org.springframework.data.repository.CrudRepository;

public interface WishListRepository extends CrudRepository<WishList, Integer> {
}
