package com.example.Dec2024AmitCartV3New.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dec2024AmitCartV3New.models.CartProduct;

import jakarta.transaction.Transactional;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Integer>{
	
	Optional<CartProduct> findByCartUserUserIdAndProductProductId(Integer userId, Integer productId);
	
	@Transactional
	void deleteByCartUserUserIdAndProductProductId(Integer userId, Integer productId);
}
