package com.examplejwt.demojwtauthentication.config;

import com.examplejwt.demojwtauthentication.jwtUtilHelper.JwtUtil;
import com.examplejwt.demojwtauthentication.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //before executing any authenticated api.. first request has to pass this filkter
        //once jwt authenticates token and everything then api code is executed

        //get jwt
        //check starts with Bearer
        //validate token

        //we will get header in httpServletRequest request
        String requestTokenHeader=request.getHeader("Authorization");//pass key that passed in Postman header
        String username=null;//we will get this from above token generated
        String jwtToken = null;//we will get this from above token generated

        if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken=requestTokenHeader.substring(7);
            try {
                username=jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                e.printStackTrace();
            }

            UserDetails userDetails=myUserDetailService.loadUserByUsername(username);

            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    //token is valid now do authentication.. set authentication
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                else {
                    System.out.println("token invalid");
                }
            }
            else {
                System.out.println("username is null or security context is not null");
            }

        }
        filterChain.doFilter(request,response);//everything is good.. let request, response go further

    }
}
