package com.example.AmitCartV3NewTcs.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.AmitCartV3NewTcs.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtUtill jwtUtill;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String tokenHeader = request.getHeader("Authentication");
		String tokenHeader = request.getHeader("Jwt");
		String username = null;
		String token = null;	
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7);
			username = jwtUtill.getUsernameFromToken(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
				boolean validateToken= jwtUtill.validateToken(token, userDetails);
				if (validateToken) {
					UsernamePasswordAuthenticationToken authTRoken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authTRoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authTRoken);
				}
				else {
					System.out.println("token is invalid");
				}
			}
			else {
				System.out.println("username is null or SecurityContextAuthentication is already set, not null");
			}
		}
		else {
			System.out.println("tokenHeader is null or tokenHeader does not start with Bearer");
		}
		filterChain.doFilter(request, response);
	}

}
