package com.example.demoTcsArtWorkNov23Innovator.security;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.service.LoginService;
import com.example.demoTcsArtWorkNov23Innovator.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
    JWTUtil jwtUtil;

	@Autowired
    LoginService loginService;

	@Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain filterChain) throws ServletException, IOException {
    	String tokenHeader = request.getHeader("Authorization");
    	String token = null;
    	String username = null;
    	
    	if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
    		token = tokenHeader.substring(7);
    		username = jwtUtil.getUsernameFromToken(token);
    		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
    			UserModel userModel = userService.getUserByUsername(username);//eralier not writing this line
    			//earlier we were directly getting email from getUsernameFromToken 
    			//but now we need are getting username so need to call getByUsername
    			UserDetails userDetails = loginService.loadUserByUsername(userModel.getEmail());
    			Boolean validateToken = jwtUtil.validateToken(token, userDetails);
    			if(validateToken) {
    				UsernamePasswordAuthenticationToken authToken = 
        					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    				SecurityContextHolder.getContext().setAuthentication(authToken);
    			}
    			else {
    				System.out.println("error in jwt filter");
    			}
    		}
    		else {
				System.out.println("error in jwt filter");
			}
    	}
    	else {
			System.out.println("error in jwt filter");
		}
    	filterChain.doFilter(request, response);
    }
}
