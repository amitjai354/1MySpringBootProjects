package com.example.Innovator2025June28.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Innovator2025June28.config.UserInfoUserDetailsService;
import com.example.Innovator2025June28.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenHeader = request.getHeader("Authorization");
		//ab finally station add par ye error aa rhi : Cannot invoke "String.startsWith(String)" because "token" is null
		//actually i had written here : token.startWith("Bearer ") instead of tokenHeader , so it was null
		//yad rjhna ye error bhi
		String token = null;
		String username = null;
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token=tokenHeader.substring(7);
			username=jwtService.extractUsername(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Boolean validateToken = jwtService.validateToken(token, userDetails);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				else {
					System.out.println("amit Filter validate token");
				}
			}
			else {
				System.out.println("amit Filter username");
			}
		}
		else {
			System.out.println("amit Filter tokenheader bearer");
		}
		filterChain.doFilter(request, response);
	}

}
