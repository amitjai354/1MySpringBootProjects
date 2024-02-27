package com.examplejwt.demojwtauthentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        //when we are hitting api without passing token.. we are getting 403 Forbidden
        //But we want 401 unauthorised
        //this commence method will reject all unauthenticated errors and will send error code 401

        //we have to send error in response so will use response
        //response.sendError(401, "Unauthorized");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied");
    }
}
