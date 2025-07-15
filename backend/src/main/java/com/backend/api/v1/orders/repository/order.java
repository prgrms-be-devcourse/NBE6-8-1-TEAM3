package com.backend.api.v1.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface order extends JpaRepository<order, Integer> {
}
