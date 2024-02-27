package com.example.demo.amitSbProject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {
    @GetMapping("/cart")
    public ResponseEntity<Object> cgetCart(){
        return null;
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
