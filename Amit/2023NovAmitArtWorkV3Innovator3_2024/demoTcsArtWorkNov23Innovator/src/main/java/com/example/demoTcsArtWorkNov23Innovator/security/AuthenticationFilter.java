package com.example.demoTcsArtWorkNov23Innovator.security;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.UserRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	@Autowired
	UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String tokenHeader = request.getHeader("Authorization");
       String token = null;
       String username = null;
       if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
    	   token = tokenHeader.substring(7);
    	   username = jwtUtil.getUsernameFromToken(token);
    	   
//    	   UserModel usermodel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("invalid username"));
//    	   String email = usermodel.getEmail(); 
    	   //use this if not setting email in subject
    	   
    	   if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null) {
    		   UserDetails userDetails = loginService.loadUserByUsername(username);//use this if setting email in subject, directly getting email inusername
    		   //UserDetails userDetails = loginService.loadUserByUsername(email);//use this if setting
    		   Boolean validateToken = jwtUtil.validateToken(token, userDetails);
    		   if(validateToken) {
    			   UsernamePasswordAuthenticationToken authToken = new 
    					   UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    			   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    			   SecurityContextHolder.getContext().setAuthentication(authToken);
    		   }
    	   }
       }
       filterChain.doFilter(request, response);
    }
}
