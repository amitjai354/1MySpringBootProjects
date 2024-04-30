package com.example.demo.amitSbProject.services;

import com.example.demo.amitSbProject.repositories.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;

//    public ResponseEntity<Object> getCart(){
//        try{
//            return cartRepo.findAll();
//        }
//        catch (Exception e){
//            ResponseEntity.status(HttpStatus.)
//        }
//
//    }
}
