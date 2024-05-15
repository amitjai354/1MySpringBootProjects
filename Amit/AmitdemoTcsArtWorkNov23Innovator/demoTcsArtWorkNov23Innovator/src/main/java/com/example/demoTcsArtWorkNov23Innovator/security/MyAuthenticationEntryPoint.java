package com.example.demoTcsArtWorkNov23Innovator.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


//by default 403 is send but we send 401 UnAuthorised with this for all request if they need to be authenticated
//but we are providing authentication details
//if not passing any token then getting msg and everything 401
//but if token is passed for customer then that is Forbidden 403 and not coming from entry ponit
//but from request matchert this is coming
//in appplication properties if include msg always then msg field in respone but msg is forbidden only
//that msg not updating from here
//you can do has AnyAuthority(owner, customner) in requst Matcher and if id does not match then snd this msg
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //response.sendError(HttpServletResponse.SC_FORBIDDEN,"you don't have permission");
        //response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Bad Request was made!!");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.getWriter().write("{\"message\":\"you don't have permission\'}");
        //response.getWriter().write("you don't have permission"+authException.getMessage());
        response.getWriter().println("you don't have permission!! "+authException.getMessage());

    }
    //used to return 403 instead of 400
}
