package com.example.demo.amitSbProject.configs;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
