package com.example.jbdl.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication// ->@SpringBootConfiguration -> @Configuration -> @Component
public class RestApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApisApplication.class, args);
	}

}

/*
Spring will create object of all those classes only which are annotated with @Componenet on top of it
either directly or indirectly
*/