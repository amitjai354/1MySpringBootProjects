package com.examplejwt.demojwtauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/welcome")
    public String welcome(HttpServletResponse response){
		
		//if let product is already there in cart and we are adding same product again.. coinflict with current state.. 409
		// response.setStatus(HttpServletResponse.SC_CONFLICT);
		 //response.setStatus(409,"Product already present in cart");
		
        String text="welcome to private page";
        text+="this page is not allowed for unauthenticated users";
        return text;
    }
    @GetMapping("/getUser")
    public String getUser(){
        String text="{\"amit\":\"amit123\"}";//using inverted double comma inside another doble comma
        return text;
    }
}
