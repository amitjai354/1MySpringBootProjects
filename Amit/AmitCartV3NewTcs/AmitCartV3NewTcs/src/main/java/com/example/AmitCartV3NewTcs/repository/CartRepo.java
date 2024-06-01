package com.example.AmitCartV3NewTcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	List<Cart> findByUserUserId(Integer userId);
	//same way there can findByCartIdAndUserUserId(int cartId, int userId)

}
