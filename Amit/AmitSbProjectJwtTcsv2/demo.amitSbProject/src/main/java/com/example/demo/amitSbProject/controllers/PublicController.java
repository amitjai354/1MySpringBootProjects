package com.example.demo.amitSbProject.controllers;

import com.example.demo.amitSbProject.models.MyUser;
import com.example.demo.amitSbProject.models.Product;
import com.example.demo.amitSbProject.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/test")
    public String test(){
        return "Hello";
    }

    /*
    never forget to add getter, setter at entity class otherwise empty list returned always..
    Most imp that in request param.. never use "" while calling api in browser otherwise empty list returned
    http://localhost:8080/product/search?keyword=pain   this is correct..
    http://localhost:8080/product/search?keyword="pain"   this is wrong..

    even in baeldubng site.. not passing string params in quotes in api calling.. but tcs had given it in quotes
    in the example..  so that is wtrong..
     */

    //if we do not pass keyword, automatically gives 400 bad request, need not to do anything
    @GetMapping("/product/search")
    public List<Product> getProducts(@RequestParam("keyword") String keyword){
        return productRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);
        //we need to pass keyword two times as function in repo class is forcing to take 2 params if we are searching
        //by productname and categoryname
        //@RequestParam String keyword.. here both the variable name and the parameter name are the same.
    }

    @GetMapping("/test1")  //http://localhost:8080/test1?keyword=blet
    public List<Product> getP(@RequestParam("keyword") String keyword){
        System.out.println(productRepo.findByProductNameContaining("ablet"));
        System.out.println();
        System.out.println(keyword);
        System.out.println(productRepo.findByProductNameContaining(keyword));
        return productRepo.findByProductNameContaining(keyword);
    }

    @GetMapping("/test2")
    public List<Product> getP2(){
        return productRepo.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MyUser myUser){
        //to take response body from api as input, need one class that has username and password as attribute
        //getting 403 Forbidden on wrong credentials, but it should give 401 so create class to send 401
        return null;
    }
}
