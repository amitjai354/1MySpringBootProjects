package com.example.BirthCertificate.security;

import com.example.BirthCertificate.service.LoginService;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtill jwtUtill;

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authentication");
        String token = null;
        String username = null;
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            token=tokenHeader.substring(7);
            username=jwtUtill.getUserNameFromToken(token);
            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = loginService.loadUserByUsername(username);
                Boolean validateToken = jwtUtill.validateToken(token, userDetails);
                if (validateToken){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                else{
                    System.out.println("invalid token");
                }
            }
            else {
                System.out.println("username is null or authentication is already set");
            }
        }
        else {
            System.out.println("token header is null or token header does not start with Bearer");
        }
        filterChain.doFilter(request, response);
    }
}
