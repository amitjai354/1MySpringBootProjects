package com.example.Innovator2025DecemberMostImp.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Innovator2025DecemberMostImp.config.UserInfoUserDetailsService;
import com.example.Innovator2025DecemberMostImp.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username = null;
		String token = null;
		String tokenHeader = request.getHeader("Authentication");
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7);
			username = jwtService.extractUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Boolean validateToken = jwtService.validateToken(tokenHeader, userDetails);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authtoken = new
							UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authtoken);
				}
				else {
					System.out.println("amit error in auth filter");
				}
			}
			else {
				System.out.println("amit error in auth filter");
			}
		}
		else {
			System.out.println("amit error in auth filter");
		}
		
		filterChain.doFilter(request, response);
	}

}
