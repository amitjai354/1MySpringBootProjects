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


public class AuthenticationFilter extends OncePerRequestFilter {


    JWTUtil jwtUtil;


    LoginService loginService;


    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       
    }
}
