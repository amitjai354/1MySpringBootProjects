package com.example.demo.amitSbProject.repositories;

import com.example.demo.amitSbProject.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
}
