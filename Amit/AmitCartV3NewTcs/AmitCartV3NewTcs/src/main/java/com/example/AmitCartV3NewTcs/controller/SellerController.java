package com.example.AmitCartV3NewTcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	SellerService sellerService;
	
	@GetMapping("/product")
	public ResponseEntity<Object> findAllProductOwnedBySeller(){
		return sellerService.findAllProductOwnedBySeller();
	}
	
	@GetMapping("/product/{productId}") //url is /seller/product/1 not /seller/product/id=1 this is wrong
	public ResponseEntity<Object> findProductByProductIdAndOwnedBySeller(@PathVariable("productId") int productId){
		return sellerService.findProductByProductIdAndOwnedBySeller(productId);
	}
	
	@PostMapping("/product")
	public ResponseEntity<Object> saveProductBySellerId(@RequestBody Product product){
		return sellerService.saveProductBySellerId(product);
	}
	
	//Put vs Patch, in put we take complete object but in patch we can take only fields that we want to update 
	//in json.. patchj
	@PatchMapping("/product")
	public ResponseEntity<Object> updateProductBySellerId(@RequestBody Product product){
		return sellerService.updateProductBySellerId(product);
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Object> deleteProductBySellerId(@PathVariable("productId") int productId){
		return sellerService.deleteProductBySellerId(productId);
	}

}
