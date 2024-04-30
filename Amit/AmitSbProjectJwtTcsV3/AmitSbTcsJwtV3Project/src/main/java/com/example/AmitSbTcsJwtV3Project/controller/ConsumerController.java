package com.example.AmitSbTcsJwtV3Project.controller;

import com.example.AmitSbTcsJwtV3Project.model.Cart;
import com.example.AmitSbTcsJwtV3Project.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/auth/consumer")
public class ConsumerController {

    @Autowired
    CartRepo cartRepo;

    @GetMapping("/cart")
    public ResponseEntity<Object> getCart(){
        List<Cart> allCart = cartRepo.findAll();
        if(allCart.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(cartRepo.findAll());
    }

    @PostMapping("/cart")
    public ResponseEntity<Object> postCart(){
        return null;
    }

    @PutMapping("/cart")
    public ResponseEntity<Object> putCart(){
        return null;
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Object> deleteCart(){
        return null;
    }
}
