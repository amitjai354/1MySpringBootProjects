package filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import config.UserInfoUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.JwtService;

@Component
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
	private UserInfoUserDetailsService myUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String  tokenHeader = request.getHeader("Authorization");
		//String  tokenHeader = request.getHeader("Authentication");
		String token=null;
		String username=null;
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7);
			username = jwtService.getUsernameFromToken(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
				Boolean validateToken = jwtService.validateToken(token, userDetails);
				if(validateToken) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				else {
					System.out.println("Token Invalid!!");
				}
			}
			else {
				System.out.println("username is null or authentication already set");
			}
		}
		else {
			System.out.println("Token is null or does not start with Bearer");
		}
		filterChain.doFilter(request, response);
	}
}
