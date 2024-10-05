package com.example.AmitCartV3NewTcs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	Optional<Cart> findByUserUserId(Integer userId);//this is one to one , one user has one cart only so not a list
	//same way there can findByCartIdAndUserUserId(int cartId, int userId)

}
