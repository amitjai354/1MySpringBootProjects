package com.example.Innovator24June.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Innovator24June.config.UserInfoUserDetailsService;
import com.example.Innovator24June.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter{

	//I have written Authentication in header as we always used to do
	//but in exam they are passing "Authorization" in header..
	//I had given 2 min to find this in question paper but they had not given clearly anything
	//Token generate bhi ho jata to kabhi test case pass na hote api wale because of this
	//must check test cases as well
	//also take screen shot of imports and complete pom.xml properly
	
//	@Test
//	void i_getSuccessTicketCheckTest() throws Exception { //if caausing any issue then comment this test case, 
//		//remaining test cases should pass.. this we can verify in postman output also.. just check output
//		
//		//to get the ticket successfully using category
//		mockMvc.perform(get("/ticket/list?category=Hardware").contentType(MediaType.APPLICATION_JSON_VALUE)
//				.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1)))
//		.andExpect(MockMvcResultMatchers.status().isOk())
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoUserDetailsService userInfoUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenHeader = request.getHeader("Authorization");
		String username=null;
		String token = null;
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7);
			username = jwtService.getUsernameFromToken(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(username);
				Boolean validateToken = jwtService.validateToken(token, userDetails);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authToken = new 
							UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				else {
						System.out.println("Invalid token");
					}
			}
			else {
						System.out.println("Invalid token");
					}
		}
		else {
						System.out.println("Invalid token");
		}
		//must write these else.. i missed and wrote filterchain in above bracket.. inside one if block..
		//so without filter chain , all the apis started giving 200, 
		//even the wrong urls were not giuving not found 404, instead they all were giving 200
		//as all the apis were acting without any filter
		filterChain.doFilter(request, response);
	}
}
