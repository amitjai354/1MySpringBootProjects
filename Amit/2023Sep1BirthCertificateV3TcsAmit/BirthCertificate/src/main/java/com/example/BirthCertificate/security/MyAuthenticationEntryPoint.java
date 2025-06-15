package com.example.BirthCertificate.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        //response.getWriter().println("You don't have permission!! "+authException.getMessage());
        //response.sendError(HttpServletResponse.SC_BAD_REQUEST);//Not printing msg with sendError in Postman
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("You don't have permission!! "+authException.getMessage());
    }
}
