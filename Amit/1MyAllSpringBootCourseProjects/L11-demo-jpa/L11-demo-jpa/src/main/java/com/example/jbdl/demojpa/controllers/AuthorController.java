package com.example.jbdl.demojpa.controllers;

import com.example.jbdl.demojpa.requests.AuthorCreateRequest;
import com.example.jbdl.demojpa.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    public void insertAuthor(@Valid @RequestBody AuthorCreateRequest authorCreateRequest){
        authorService.insert(authorCreateRequest.to());
        //we are  passing author create request to author service from there to repository.. there fetch the values of
        //name, age, country from this object and push in db
    }
}
