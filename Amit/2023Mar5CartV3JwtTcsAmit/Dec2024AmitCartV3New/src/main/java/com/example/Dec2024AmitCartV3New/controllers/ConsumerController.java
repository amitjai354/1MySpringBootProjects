package com.example.Dec2024AmitCartV3New.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dec2024AmitCartV3New.models.Cart;
import com.example.Dec2024AmitCartV3New.models.CartProduct;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.CartProductRepo;
import com.example.Dec2024AmitCartV3New.repo.CartRepo;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartProductRepo cartProductRepo;
	
	@Autowired
	UserRepo userRepo;
	
	

	@GetMapping("/cart")
	public ResponseEntity<Object> getCart(){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Cart cartFromDb = cartRepo.findByUserUsername(userDetails.getUsername()).orElse(null);
			//each consumer will have 1 cart id.. in api response also not given list[] but single cart{}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(cartFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
		
	}
	
	@PostMapping("/cart")
	public ResponseEntity<Object> postCart(@RequestBody Product product){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));   
			
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(user.getUserId(), product.getProductId()).orElse(null);  
			if(cartProductFromDb == null) {
				return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("product already added in cart");
			}
			
			Product productFromDb = productRepo.findById(product.getProductId()).orElse(null);
			if(productFromDb == null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("product out of stock");
			}
			
			Cart cartFromDb = cartRepo.findByUserUsername(userDetails.getUsername()).orElse(null);
			if(cartFromDb==null) {
				cartFromDb = cartRepo.save(new Cart(0.0, user));
			}
			
			Double amount = product.getPrice();
			cartFromDb.setTotalAmount(amount);
			
			cartFromDb = cartRepo.save(cartFromDb);
			
			cartProductFromDb = cartProductRepo.save(new CartProduct(cartFromDb, productFromDb, 1));
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(cartProductFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
	
	@PutMapping("/cart")
	public ResponseEntity<Object> putCart(@RequestBody CartProduct cartProduct){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));   

			//first of all check if this user has cart or not
			Cart cartFromDb = cartRepo.findByUserUsername(userDetails.getUsername()).orElse(null);
			if(cartFromDb == null) {
				cartFromDb = cartRepo.save(new Cart(0.0, user));
			}
			
			//if cart product in db , then it must have user details as well so do not look at request as
			//we are fetching from db, from request we will take product id only, userid we will take from
			//logged in user not cart product in request
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(user.getUserId(), cartProduct.getProduct().getProductId()).orElse(null);
			//if cart product not in cart, add to cart
			
			
			Double oldCartAmount = cartFromDb.getTotalAmount();
			Double newCartAmount =0.0;
			//if cart product in cart and supplied quantity = 0 then delete from cart
			if(cartProductFromDb!= null && cartProduct.getQuantity()==0) {
				newCartAmount = oldCartAmount-(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice());
				cartFromDb.setTotalAmount(newCartAmount);
				cartFromDb = cartRepo.save(cartFromDb);
				
				cartProductRepo.deleteByCartUserUserIdAndProductProductId(user.getUserId(), cartProductFromDb.getProduct().getProductId());
				return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
			}
			
			
			if(cartProductFromDb==null) {
				newCartAmount= oldCartAmount+(cartProduct.getQuantity()*cartProduct.getProduct().getPrice());
			}
			else {
				newCartAmount = oldCartAmount-(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice())
						+(cartProduct.getQuantity()*cartProduct.getProduct().getPrice());
			}
			
			cartFromDb.setTotalAmount(newCartAmount);
			cartFromDb = cartRepo.save(cartFromDb);
			
			cartProductFromDb.setCart(cartFromDb);
			cartProductFromDb.setQuantity(cartProduct.getQuantity());
			
			cartProductFromDb = cartProductRepo.save(cartProductFromDb);
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(cartProductFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
	
	@DeleteMapping("/cart")
	public ResponseEntity<Object> deleteCart(@RequestBody Product product){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));   

			//we have product in request, remove it from cart
			//we have product id and user id, find cartProduct by user id and product id if cartProduct not in db
			//then return product not present else delete
			//when delete, reduce amount from cart, delete from cartProduct as product id in cartProduct
			
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(
					user.getUserId(), product.getProductId()).orElse(null);
			if(cartProductFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("product not in cart");
			}
			
			Cart cartFromDb = cartRepo.findByUserUsername(user.getUsername()).orElse(null);
			//this is not possible that cartproduct is in cart but there is no cart
			//so no need to check null case here, but still lets put the check, np
			if(cartFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("No cart present for logged in user");
			}
			
			double oldCartAmount = cartFromDb.getTotalAmount();
			double newCartAmount = oldCartAmount-(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice());
			
			cartFromDb.setTotalAmount(newCartAmount);
			cartFromDb = cartRepo.save(cartFromDb);
			
			cartProductRepo.deleteByCartUserUserIdAndProductProductId(user.getUserId(), product.getProductId());
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body("deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
}
