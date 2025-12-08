package com.example.Innovator2025June28.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Innovator2025June28.config.UserInfoUserDetailsService;
import com.example.Innovator2025June28.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//String tokenHeader = request.getHeader("Authorization"); //insteasd of tokenHeader , use authHeader so less chances of error here
		String authHeader = request.getHeader("Authorization");
		//ab finally station add par ye error aa rhi : Cannot invoke "String.startsWith(String)" because "token" is null
		//actually i had written here : token.startWith("Bearer ") instead of tokenHeader , so it was null
		//yad rkhna ye error bhi
		String token = null;
		String username = null;
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token=tokenHeader.substring(7);
			username=jwtService.extractUsername(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Boolean validateToken = jwtService.validateToken(token, userDetails);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					//I had missed ! in the validateToken code, so this validate token was failing so, 
					//api was directly giving 401 even without hittinhg any api
					//if filter fails, request will be rejected immediately and authentication enyry point will give 401 unauthenticated
					//debug point of test case will not go to the api code even, it immediatly give api failed
					//but everything ok then before hitting test case coede, api code is hit if we put debug point in api code and test case code
				}
				else {
					//this is must to write as if let say validation was failing dute to wrong code of jwt validate
					//we will get one sentence in the log that filter validation failed
					System.out.println("amit Filter validate token");
				}
			}
			else {
				System.out.println("amit Filter username");
			}
		}
		else {
			System.out.println("amit Filter tokenheader bearer");
		}
		filterChain.doFilter(request, response);
	}

}
