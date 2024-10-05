package com.example.Innovator24June.config;


import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint{
	//this class was not given in exam, I created this.. check if needed or not based on question paper
	
//	//check unauthorized access //unauthorized means no access at all
//			mockMvc.perform(post("/ticket/add")
//					.content(toJson(ticketModel))
//					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
//			
//			//check forbidden access // forbidden means has some access but not all, here checks Authorization bearer token
//			mockMvc.perform(post("/ticket/add")
//					.content(toJson(ticketModel))
//					.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1))
//					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().println("You don't have permission to access this "+authException.getMessage());
	}
	
}
