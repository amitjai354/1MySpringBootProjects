package org.example.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.CreateInMemoryUser_UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger1 = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserDetailsService userDetailsService;
    //CreateInMemoryUser_UserDetailService createInMemoryUserUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authentication");
        logger1.info("Header is: {}", requestHeader);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7);
            try {
                username=jwtHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e){
                logger1.info("Illegal argument while fetching the username !!");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e){
                logger1.info("Given jwt token is expired !!");
                e.printStackTrace();
            }
            catch (MalformedJwtException e){
                logger1.info("Some changes has been done in the token !! Invalid Token");
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            logger1.info("Invalid Header Value !!");
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            //if user is not authenticated yet then we will authenticate it using usernamePassword Authentication
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validateToken = jwtHelper.validateToken(token, userDetails);
            if (validateToken){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else {
                logger1.info("validation failed !!");
            }
        }
            filterChain.doFilter(request, response);
    }
}
