package com.example.AmitCartV3NewTcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepo extends JpaRepository<Product, Integer>{

	//@Query(nativeQuery = true, value = "select p.category_id, p.price, p.product_id, p.seller_id, p.product_name from Product p, Category c where p.category_id = c.category_id and upper(p.product_name) like '%' || ?1 || '%' or upper(c.category_name) like '%' || ?2 || '%'")
	@Query(nativeQuery = true, value = "select p.category_id, p.price, p.product_id, p.seller_id, p.product_name from Product p, Category C where p.category_id = c. category_id and upper(p.product_name) like upper('%' || ?1 '%') or upper(c.category_name) like upper('%' || ?2 || '%') ")
	List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameIgnoreCaseContaining(String productName, String categoryName);
	
	List<Product> findBySellerUserId(int sellerId);
	
	Product findByProductIdAndSellerUserId(int productId, int sellerId);
}
