package com.example.AmitCartV3NewTcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.CartProduct;

public interface CartProductRepo extends JpaRepository<CartProduct, Integer>{

}
