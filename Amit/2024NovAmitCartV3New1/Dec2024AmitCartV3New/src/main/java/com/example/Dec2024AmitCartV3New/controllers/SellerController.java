package com.example.Dec2024AmitCartV3New.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {
	
	//status 200
	@PostMapping("/product")
	public ResponseEntity<Object> postProduct(){
		return null;
	}

	//status 200
	@GetMapping("/product")
	public ResponseEntity<Object> getAllProducts(){
		//return all product owned by seller, not other sellers
		//in cadt repo can not do find by id as will fetch all details other than product
		//product has category id and seller id
		//inside product repo..  findByUserSellerId
		//why working withpuit category id
		return null;
	}
	
	//status 200
	@GetMapping("/product/{productId}")
	public ResponseEntity<Object> getProduct(){
		//in product repo, findById.. can not do this as will fetch other sellers product as well
		//product has category id and seller id
		//inside product repo..  findbyProductIdAndUserSellerId
		//findbyProductIdAndUserSellerIdAndCategoryCategoryId  why not this.. we have json ignore at category id
		//but that is at java input and response level, in table we have category id
		return null;
	}

	//status 200
	@PutMapping("/product")
	public ResponseEntity<Object> putProduct(){
		return null;
	}
	
	//status 200
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Object> deleteProduct(){
		return null;
	}
}
