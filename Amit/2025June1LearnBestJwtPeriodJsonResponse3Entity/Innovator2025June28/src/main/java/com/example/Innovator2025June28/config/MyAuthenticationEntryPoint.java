package com.example.Innovator2025June28.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().println("you do not have permission "+authException.getMessage());
		//write this line in practise but not in exam, every minute is very important
		//kuch galat ho gya to lene ke dene pad jayenge, also anxietyu me har cheez confusing lagti hai bhot inb exam
	}

}
