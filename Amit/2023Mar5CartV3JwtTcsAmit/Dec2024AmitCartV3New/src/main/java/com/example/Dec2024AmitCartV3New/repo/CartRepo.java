package com.example.Dec2024AmitCartV3New.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dec2024AmitCartV3New.models.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{
	
	Optional<Cart> findByUserUsername(String username);
}
