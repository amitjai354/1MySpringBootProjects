package com.example.AmitCartV3NewTcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.AmitCartV3NewTcs.model.Cart;
import com.example.AmitCartV3NewTcs.model.CartProduct;
import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.model.UserModel;
import com.example.AmitCartV3NewTcs.repository.CartProductRepo;
import com.example.AmitCartV3NewTcs.repository.CartRepo;
import com.example.AmitCartV3NewTcs.repository.ProductRepo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class CartService {
	
}
