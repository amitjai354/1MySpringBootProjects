package com.example.innovator24Dec.config;


import org.springframework.http.MediaType;

public class MyAuthenticationEntryPoint {
	
	/*
	
	//unauthorised access //if Token not passed at all.. MyauthenticationentryPoint is used to send unauthorised
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();

	//check forbidden access //here token passed but invalid token then forbidden
	this is handled in security configuration.. request matcher and some cases handled in api codes eg wrong user
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_USER_1))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
		
	*/

	
	//as per test case, when no bearer token passed it should give unauthorized.. handle this here
	//if token passed but user is not correct.. forbidden.. handle in security config filter chain .. has Authority
	
	//check if we do not write this authentication entry pointthen are failed ticket testcase failing ..
	//as this whole class was not given in the exam.. i added it by myself
	//they want unauthorized status if not passing token, and that is done from here only 
	//before accessing any any, this entry point runs and if no token, gives unauthorized for all apis
	
	
}
