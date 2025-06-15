package com.example.innovator24Dec.config;


import org.springframework.http.MediaType;

public class MyAuthenticationEntryPoint {
	
	/*
	
	//unauthorised access //if Token not passed at all.. MyauthenticationentryPoint is used to send unauthorised
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();

	//check unauthorised access //here token passed but invalid token then forbidden
	this is handled in security configuration.. request matcher and some cases handled in api codes eg wrong user
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_USER_1))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
		
	*/

}
