package com.example.Dec2024AmitCartV3New.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dec2024AmitCartV3New.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
	List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName,
			String categoryName);
	
	List<Product> findBySellerUserId(Integer sellerId);
	
	Optional<Product> findBySellerUserIdAndProductId(Integer sellerId, Integer productId);
}