package com.example.Dec2024AmitCartV3New.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {

	//status 200
	@GetMapping("/cart")
	public ResponseEntity<Object> getCart(){
		//return the consumers cart..
		//get consumer id from security context holder then in cart we have user id..
		//in cart Repo findByUserUserId
		//but we will get username from userdetail so .. 
		//findByUserUsername
		return null;
	}
	
	//status 200, 409 if already in cart
	@PostMapping("/cart")
	public ResponseEntity<Object> postCart(){
		//take product and add to cart.. product has catrgoty id, user id, in input json we get category id
		//user id we will add from security context holder
		//cart has user id only but in java code you can check that it has cart product as well,, bidirectional 
		//relationship..mapped by , alos can check in the diagram that cart has relation with user and cart product
		//so we need one cart prodct as well.. cart product has product id and cart id..
		// so save product first then save cart product then save cart.. otherwise intransient Foreign key error
		//that first add in parent class then add in child class
		
		//also need to check if this cunsumer has a cart or not if not, create new cart for this consumer first
		//how will add cart product to cart.. actually in db FK cartid is in cart product.. so will update cart id
		//in cart product.. thks way cart added to cart product
		return null;
	}
	
	//status 200
	@PutMapping("/cart")
	public ResponseEntity<Object> putCart(){
		//takes cart product json in input and update quantity in cart.. then price will also be be updated
		//if product not in cart then add to cart
		//if quantuty 0 then delete product from cart and reduce price from cart, delete query written in below api
		
		//cart product json has already product in input json..
		//we will update quantity in cart product and price we will update in cart prudct after adding cart product
		//to cart, here also if no cart create cart first
		return null;
	}
	
	//status 200
	@DeleteMapping("/cart")
	public ResponseEntity<Object> deleteCart(){
		//takes product json in input and remove product from cart
		//product has category id in input and user id we will add from security cointext holder
		//cart product has product id and cart id.. we will calculate the price of this product in cart product
		//then delete cart product from cart as cart product has cart id and then reduce the amount from cart
		//this all should be transactional in nature means either all code is executed or none is executed
		
		//when multiple entities are involved then we have to write @Transactional over delete query in repo
		
		//here we dont have to delete cart, just delete product from cart..
		//so deletById in cart repo will not work..
		
		//instead in cartProduct repo we have to write deleteBy
		//CartProduct has product id and cart id as fk.. so
		//deleteByCartUserUserIdAndProductProductId
		return null;
	}
}
