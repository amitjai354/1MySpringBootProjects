package com.example.AmitCartV3NewTcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AmitCartV3NewTcs.model.CartProduct;
import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.service.CartService;
import com.example.AmitCartV3NewTcs.service.ConsumerService;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	
	@Autowired
	ConsumerService consumerService;
	
	@GetMapping("/cart")
	public ResponseEntity<Object> getConsumerCart(){
		return consumerService.getConsumerCart();
	}
	
	@PostMapping("/cart")
	public ResponseEntity<Object> postProductToCart(@RequestBody Product product){
		return consumerService.postProductToCart(product);
	}
	
	@PutMapping("/cart")
	public ResponseEntity<Object> updateConsumerCart(@RequestBody CartProduct cartProduct){
		return consumerService.updateConsumerCart(cartProduct);
	}
	
	@DeleteMapping("/cart")
	public ResponseEntity<Object> deleteProductFromConsumerCart(@RequestBody Product product){
		return consumerService.deleteProductFromConsumerCart(product);
	}

}
