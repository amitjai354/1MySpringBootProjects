package com.example.Dec2024AmitCartV3New.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().println("You dont have permission "+authException.getMessage());
		
		//if trying to access public api login to generate jwt and password is wrong then also 401 unauthorized
		//as currently user is not logged in, password is wrong
		
		// if authenticated endpoints are accessed without JWT, return 401... 
		//here this authentication entry point works
		
		// if a consumer endpoint is accessed with seller JWT or viceverse, return 403... this we do Security
		//Configuration bean
		
		//if seller is accessing the seller api let say delete api but this seller is not the owner of this product
		//to be deleted then return Forbidden 403 or bad request 400.. this we check by getting logged in user
		//from Security context holder and then check if owner id is same as this logged inuser, 
		//if not return status 400 or 400 using Response entity
	}

}
