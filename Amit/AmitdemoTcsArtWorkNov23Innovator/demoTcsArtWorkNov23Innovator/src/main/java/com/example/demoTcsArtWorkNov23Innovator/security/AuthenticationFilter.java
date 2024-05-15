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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //we are gettiong request and response in input so means header code here
        //here HttpServletResponse but in SecurityFilterChain HttpSecurity is passed
        String tokenHeader = request.getHeader("Authentication");
        String username=null;
        String token=null;
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            token=tokenHeader.substring(7);
            //this username is actually email as in jwt request we are passing email and password to authenticate
            try{
                username= jwtUtil.getUsernameFromToken(token);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            //since we got username so we will check if username is null and also check if authentication is null
            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                //we need user details and userModel implements user details
                //once got the user name get userdetails
                //and same UsernamePasswordAuthenticationToken is used here afterall setting this autyentication only
                //also we got token so validate token also
                //UserDetails userDetails = loginService.loadUserByUsername(username);
                UserDetails userDetails= userService.getUserByUsername(username);
                Boolean validateToken = jwtUtil.validateToken(token, userDetails);
                if (validateToken){
                    //set the authentication
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                else {
                    System.out.println("Token is invalid!!");
                }
            }
            else{
                System.out.println("JWT Validation failed!!");
            }
        }
        else {
            System.out.println("Jwt Header is null!!");
        }
        filterChain.doFilter(request, response);
    }
}
