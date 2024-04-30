package com.example.AmitSbTcsJwtV3Project.config;

import com.example.AmitSbTcsJwtV3Project.service.UserAuthService;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserAuthService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("jwt");//there is one getHeaders() do not use that
        String token=null;
        String username=null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
            try {
                token = requestHeader.substring(7);
                username = jwtUtil.getUserNameFromToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Header is null");
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //once got the user name get userdetails
            //and same UsernamePasswordAuthenticationToken is used here afterall setting this autyentication only
            //also we got token so validate token also
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            Boolean validateToken = jwtUtil.validateToken(token, userDetails);
            if(validateToken){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //final set security context holder
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        else{
            System.out.println("JWT Validation failed");
        }
        filterChain.doFilter(request,response);
    }
}
