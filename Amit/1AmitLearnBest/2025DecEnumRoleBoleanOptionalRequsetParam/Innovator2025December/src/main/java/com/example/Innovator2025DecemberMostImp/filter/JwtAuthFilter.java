package com.example.Innovator2025DecemberMostImp.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Innovator2025DecemberMostImp.config.UserInfoUserDetailsService;
import com.example.Innovator2025DecemberMostImp.service.JwtService;

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
