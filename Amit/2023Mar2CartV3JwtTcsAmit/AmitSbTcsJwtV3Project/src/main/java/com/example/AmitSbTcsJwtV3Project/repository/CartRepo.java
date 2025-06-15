package com.example.AmitSbTcsJwtV3Project.repository;

import com.example.AmitSbTcsJwtV3Project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Integer> {
    Optional<Cart> findCartByUserUsername(String username);
}
