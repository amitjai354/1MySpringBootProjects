package com.example.demo.amitSbProject.controllers;

import com.example.demo.amitSbProject.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {

    @PostMapping("/product")
    public ResponseEntity<Object> postProduct(){
        return null;
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getAllProducts(){
        return null;
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getProduct(){
        return null;
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> deleteProduct(){
        return null;
    }
}
