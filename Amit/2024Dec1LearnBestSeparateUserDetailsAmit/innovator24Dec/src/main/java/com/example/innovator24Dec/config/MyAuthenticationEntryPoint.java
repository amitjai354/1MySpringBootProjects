package com.example.innovator24Dec.config;


import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //needed as this class is autowired in security config class
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint{

	
	/*
	//unauthorised access //if Token not passed at all.. MyauthenticationentryPoint is used to send unauthorised
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();

	//check forbidden access //here token passed but invalid token then forbidden
	this is handled in security configuration.. request matcher and some cases handled in api codes eg wrong user
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_USER_1))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	*/

	
	//as per test case, when no bearer token passed it should give unauthorized.. handle this here
	//if token passed but user is not correct.. forbidden.. handle in security config filter chain .. has Authority
	
	//check if we do not write this authentication entry pointthen are failed ticket testcase failing ..
	//as this whole class was not given in the exam.. i added it by myself
	//they want unauthorized status if not passing token, and that is done from here only 
	//before accessing any any, this entry point runs and if no token, gives unauthorized for all apis
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().println("you do not have permission " + authException.getMessage());
		
	}
	
}
