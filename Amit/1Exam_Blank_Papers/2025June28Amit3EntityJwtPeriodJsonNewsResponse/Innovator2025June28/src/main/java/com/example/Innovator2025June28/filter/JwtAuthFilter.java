package com.example.Innovator2025June28.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Innovator2025June28.config.UserInfoUserDetailsService;
import com.example.Innovator2025June28.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {
	
	private JwtService jwtService;
	
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
