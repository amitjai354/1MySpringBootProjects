package com.example.AmitCartV3NewTcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.CartProduct;

import jakarta.transaction.Transactional;

public interface CartProductRepo extends JpaRepository<CartProduct, Integer>{
	
	//CartProduct findByCartCartIdAndCartUserUserId(int cartId, int userId);
	//if getting by cartid and cart user id, will return any product from cart, will not consider product id
	//but in cart product there is product id also..
	//currently it is returning me product id = 2 also as this is crocin
	//but in cart product jsomn we are passing product id as 3 thyat is iphone
	CartProduct findByCartUserUserIdAndProductProductId(int userId, int productId);
	//in cart we have product and cart

	
	//whenever some other Entity or more than one entity involved in deleting it should be @Transactional
	//as data may currupt in deleting
//	@Transactional
//	void deleteByCartUserUserIdAndProductProductId(int userId, int productId);
	//void deleteByCartCartIdAndCartUserUserId(int cartId, int UserId);
	
	@Transactional
    void deleteByCartUserUserIdAndProductProductId(Integer userId, Integer productId);

}
