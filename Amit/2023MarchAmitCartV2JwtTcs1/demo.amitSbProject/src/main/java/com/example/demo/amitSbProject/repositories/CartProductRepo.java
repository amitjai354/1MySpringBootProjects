package com.example.demo.amitSbProject.repositories;

import com.example.demo.amitSbProject.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
}
