package com.example.demo.amitSbProject.configs;

import com.example.demo.amitSbProject.models.MyUser;
import com.example.demo.amitSbProject.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username=null;
        String token=null;
        String requestHeaderToken = request.getHeader("Authentication");

        if(requestHeaderToken!=null && requestHeaderToken.startsWith("Bearer")){
            //this null check is very important otherwise if we are not passing token in header then always will give error
            //as this is null so null pointer exception
            token=requestHeaderToken.substring(7);
            try{
                username=jwtUtil.getUsernameFromToken(token);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //we have username now so we will check if there is any authentication set, if not then set
                UserDetails myUserDetails = myUserDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token,myUserDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
