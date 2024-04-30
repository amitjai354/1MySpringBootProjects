package com.example.demo.amitSbProject.controllers;

import com.example.demo.amitSbProject.models.Category;
import com.example.demo.amitSbProject.models.Product;
import com.example.demo.amitSbProject.repositories.CategoryRepo;
import com.example.demo.amitSbProject.repositories.ProductRepo;
import com.example.demo.amitSbProject.repositories.UserRepo;
import com.example.demo.amitSbProject.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
//@RequestMapping("/api/auth/seller")
public class SellerController {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/product")
    //@Transactional
    public ResponseEntity<Object> postProduct(@RequestBody Product product){
        //put authorization as seller in config so that only seller can access this api not consumer
        try{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            product.setSeller(userRepo.findUserByUsername(userName));
//            Category category = categoryRepo.findById(product.getCategory().getCategoryId()).orElse(new Category());
//            if(category.getCategoryId() == product.getCategory().getCategoryId()){
//                //create your query insrt into Product and pass category id and seller
//            }
            return ResponseEntity.status(HttpStatus.CREATED).body(productRepo.saveAndFlush(product));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        //if category already exist, i should build product object without category.. but that will give null for category

        /*
         {
        "productId": 3,
        "category": {
            "categoryName":"Electronics",
            "categoryId":"2"
        },
        "price": "98000.0",
        "productName":"iPhone 12 Pro Max"
        }
         */
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") int productId){
        return ResponseEntity.ok(productRepo.findById(productId));
    }

    @PutMapping("/product")
    public ResponseEntity<Object> putProduct(){
        return null;
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Product> deleteProduct(){
        return null;
    }
}
