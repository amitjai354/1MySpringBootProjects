package com.example.Innovator24June.config;


import org.springframework.http.MediaType;

public class MyAuthenticationEntryPoint {
	//this class was not given in exam, I created this.. check if needed or not based on question paper
	
//	//check unauthorized access //unauthorized means no access at all
//			mockMvc.perform(post("/ticket/add")
//					.content(toJson(ticketModel))
//					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
//			
//			//check forbidden access // forbidden means has some access but not all, here checks Authorization bearer token
//			mockMvc.perform(post("/ticket/add")
//					.content(toJson(ticketModel))
//					.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1))
//					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
}
