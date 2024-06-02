package com.example.AmitCartV3NewTcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.Product;
import java.util.List;


public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameIgnoreCaseContaining(String productName, String categoryName);
	
	List<Product> findBySellerUserId(int sellerId);
	
	Product findByProductIdAndSellerUserId(int productId, int sellerId);
}
