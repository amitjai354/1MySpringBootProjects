package com.example.AmitCartV3NewTcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AmitCartV3NewTcs.service.CartService;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	
	@Autowired
	CartService cartService;
	
	@GetMapping("/cart")
	public ResponseEntity<Object> getConsumerCart(){
		return cartService.getConsumerCart();
	}

}
