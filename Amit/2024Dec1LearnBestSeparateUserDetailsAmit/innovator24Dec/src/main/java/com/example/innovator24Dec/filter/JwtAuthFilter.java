package com.example.innovator24Dec.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.innovator24Dec.config.UserInfoUserDetailsService;
import com.example.innovator24Dec.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //needed as we are autowiring other classes here like JwtService
public class JwtAuthFilter extends OncePerRequestFilter{
	
	//I have written Authentication in header as we always used to do
	//but in exam they are passing "Authorization" in header..
	//I had given 2 min to find this in question paper but they had not given clearly anything
	//Token generate bhi ho jata to kabhi test case pass na hote api wale because of this
	//must check test cases as well
	//also take screen shot of imports and complete pom.xml properly
	
//	@Test
//	void i_getSuccessTicketCheckTest() throws Exception { //if causing any issue then comment this test case, 
//		//remaining test cases should pass.. this we can verify in postman output also.. just check output
//		
//		//to get the ticket successfully using category
//		mockMvc.perform(get("/ticket/list?category=Hardware").contentType(MediaType.APPLICATION_JSON_VALUE)
//				.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1)))
//		.andExpect(MockMvcResultMatchers.status().isOk())

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//must write all else cases.. i missed and wrote filter chain inside last if bracket.. 
		//so without filter chain , all the api started giving 200, 
		//even the wrong urls were not giving not found 404, instead they all were giving 200
		//as all the apis were acting without any filter
		
		String tokenHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7);
			username = jwtService.extractUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				//UserModel userModel = userService.getUserByUsername(username);
				//this was done in 2023 project as there was email and username both but everwhere needed email
    			//there earlier we were directly getting email from getUsernameFromToken 
    			//but now we need are getting username so need to call getByUsername
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Boolean validateToken = jwtService.validateToken(token, userDetails);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authtokToken = new 
							UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authtokToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authtokToken);
				}
				else {
					System.out.println("issue in jwt auth filter");
				}
			}
			else {
				System.out.println("issue in jwt auth filter");
			}
		}
		else {
			System.out.println("issue in jwt auth filter");
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	
}
