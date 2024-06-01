package com.example.AmitCartV3NewTcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.repository.ProductRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;

	public ResponseEntity<Object> findProductByKeyword(String productName, String categoryName){
		try {
			List<Product> productList = productRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameIgnoreCaseContaining(productName, categoryName);
			if(productList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("product does not exist with given keyword");
			}
			return ResponseEntity.ok(productList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Some sql exception in getting product list by keywords");
		}
	}
}
