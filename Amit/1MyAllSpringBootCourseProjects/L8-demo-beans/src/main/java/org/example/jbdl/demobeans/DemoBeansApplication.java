package org.example.jbdl.demobeans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoBeansApplication {
    //we can not have more than one same api..server will not start
    //same api=same endpoint + same http method
    //if method is Get and PutMapping and endpoint is same.. server will start no error
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(DemoBeansApplication.class, args);//without this program will stop immediately
    }
}