package com.example.Dec2024AmitCartV3New.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.service.UserAuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtill jwtUtill;
	
	@Autowired
	private UserAuthService userAuthService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tokenHeader = request.getHeader("Authorization");
		String username=null;
		String token = null;
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7);
			username = jwtUtill.getUsernameFromToken(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userAuthService.loadUserByUsername(username);
				Boolean validateToken = jwtUtill.validateToken(token);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				else {
					System.out.println("invalid token");
				}
			}
			else {
				System.out.println("username is null or authentication already set");
			}
		}
		else {
			System.out.println("tokenHeader is null or does not start withh Bearer ");
		}
		
		filterChain.doFilter(request, response);
	}

}
