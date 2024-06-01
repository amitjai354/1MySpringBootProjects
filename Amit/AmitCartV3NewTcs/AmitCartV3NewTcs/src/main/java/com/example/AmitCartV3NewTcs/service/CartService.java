package com.example.AmitCartV3NewTcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.AmitCartV3NewTcs.model.Cart;
import com.example.AmitCartV3NewTcs.model.UserModel;
import com.example.AmitCartV3NewTcs.repository.CartRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CartService {
	
	@Autowired
	CartRepo cartRepo;

	public ResponseEntity<Object> getConsumerCart(){
		//it will automatically give 403 if Seller tries to access this as in security filter chain have givrn 
		//request matcher so from securirty Filter chain returns 403 we need not to set anything in 
		//authentication entry point..
		//authenbtication entry point is for when no token passed
		//but 403 will be returned only if we write anyrequest.permitAll
		//if we make it authenticatred then in all cases only authentication entry point
		//with anyrequest permitall, we are getting proper 401 when no token passed due to authentication entry point
		//and getting 403 if any sellertries to access consumer api
		
		//amit is both seller and consumer so api is working for amit but not for glaxo user seller
		try {
			UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//List<Cart> cartList = cartRepo.findAll();
			//problem with findAll is that we will get cart details of all consumers(jack and bob and amit)
			//but it should be like that which user logged in only that user should see the his cart not others cart
			//we need to make any such method where we can pass user id as well
			
			List<Cart> cartList = cartRepo.findByUserUserId(userModel.getUserId());
			//this is working perfectrly.. now getting details of only logged in consumer not all consumers
			//same way there can findByCartIdAndUserUserId(int cartId, int userId)
			
			if(cartList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("cart is empty");
			}
			return ResponseEntity.ok(cartList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
}
