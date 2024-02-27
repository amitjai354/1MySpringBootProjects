package com.example.jbdl.demobeans;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class MyRestTemplate{
    //public class MyRestTemplate extends RestTemplate {
    //now we can autowire MyRestTemplate if we etend
    //using this class we can create only one bean but we cvan not create beans of other source Library classes
    public MyRestTemplate(){
        System.out.println("Inside MyRestTemplate class that extends RestTemplate");
    }
}
