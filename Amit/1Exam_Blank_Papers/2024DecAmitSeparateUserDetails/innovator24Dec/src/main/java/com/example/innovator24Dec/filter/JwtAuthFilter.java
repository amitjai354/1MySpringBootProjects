package com.example.innovator24Dec.filter;

import com.example.innovator24Dec.config.UserInfoUserDetailsService;
import com.example.innovator24Dec.service.JwtService;

public class JwtAuthFilter {
	
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

	private JwtService jwtService;
	
	private UserInfoUserDetailsService userDetailsService;
	
	
	//must write all else cases.. i missed and wrote filter chain inside last if bracket.. 
	//so without filter chain , all the api started giving 200, 
	//even the wrong urls were not giving not found 404, instead they all were giving 200
	//as all the apis were acting without any filter
}
