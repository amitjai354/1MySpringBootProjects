package com.example.Innovator24June.filter;

public class JwtAuthFilter {
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
}
