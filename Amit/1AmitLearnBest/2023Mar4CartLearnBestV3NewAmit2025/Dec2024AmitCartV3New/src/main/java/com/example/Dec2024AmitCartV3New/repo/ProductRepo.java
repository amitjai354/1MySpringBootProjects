package com.example.Dec2024AmitCartV3New.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dec2024AmitCartV3New.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	//@Query(nativeQuery = true, value = "select p.category_id, p.price, p.product_id, p.seller_id, p.product_name from Product p, Category C where p.category_id = c.category_id and upper(p.product_name) like upper('%' || ?1 '%') or upper(c.category_name) like upper('%' || ?2 || '%') ")
	//List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameIgnoreCaseContaining(String productName, String categoryName);
	
	
	List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName,
			String categoryName);
	
	
	//Seller is User here so use attribute name for other class
	///do not use other class name directly.. findByUserUserId() not like this
	List<Product> findBySellerUserId(Integer sellerId);
	
	Optional<Product> findBySellerUserIdAndProductId(Integer sellerId, Integer productId);
	
	//created by me but not needed as we are doing find by sller id and product id so if not present giving 404
	void deleteBySellerUserIdAndProductId(Integer sellerId, Integer productId);
	
	
	//in exam, if you will select workspace inside prject, then when you will try to open project from eclipse, it will not open
	//always make workspace as DEsktop or leave whatever is there as default.
	//when opening project, then select project folder only
}
