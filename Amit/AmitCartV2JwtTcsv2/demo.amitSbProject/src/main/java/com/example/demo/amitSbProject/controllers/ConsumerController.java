package com.example.demo.amitSbProject.controllers;

import com.example.demo.amitSbProject.models.CartProduct;
import com.example.demo.amitSbProject.models.Product;
import com.example.demo.amitSbProject.repositories.CartProductRepo;
import com.example.demo.amitSbProject.repositories.CartRepo;
import com.example.demo.amitSbProject.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/auth/consumer")
public class ConsumerController {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartProductRepo cartProductRepo;

    @GetMapping("/cart")
    public ResponseEntity<Object> getCart(){
        return ResponseEntity.ok().body(cartRepo.findAll());
    }

    @PostMapping("/cart")
    public ResponseEntity<Object> postCart(@RequestBody Product product){
        //using user in cqrt get cart details, cart id
        // then using cart id get cartproduct details,, cart product id
        //then using cart product save product json in cart
        //cartRepo.save saves all cart product and cart product inturn save product
        try{
            return ResponseEntity.ok().body(productRepo.save(product));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        /*{
            "productId": 3,
                "category": {
            "categoryName":"Electronics",
                    "categoryId":2
        },
            "price": 98000.0,
                "productName":"iPhone 12"
        }
        */
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
