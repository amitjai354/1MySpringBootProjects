package com.example.Innovator24June;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dto.AuthRequest;
import entity.TicketModel;
import entity.UserInfo;
import java.util.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class Innovator24JuneApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private MockMvc mockMvc;
	
	public static final String TOKEN_ADMIN_1 = "token_admin_1";
	public static final String TOKEN_CLIENT_1 = "token_client_1";
	public static final String TOKEN_CLIENT_2 = "token_client_2";
	public static final String ID_TICKET_1 = "id_ticket_1";
	public static final String ID_TICKET_2 = "id_ticket_2";
	
	@Autowired
	WebApplicationContext context;
	
	@BeforeEach
	void setMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	@Test
	void a_userAddTest() throws Exception{
		UserInfo user1 = new UserInfo(1, "clientOne", "client1@gmail.com", "client123$", "CLIENT");
		MvcResult result1 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(user1)))
			.andExpect(status().is(201))
			.andReturn();
		
		UserInfo user2 = new UserInfo(2, "clientTwo", "client2@gmail.com", "client456$", "CLIENT");
		MvcResult result2 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(user2)))
			.andExpect(status().is(201))
			.andReturn();
		
		UserInfo user3 = new UserInfo(2, "admin", "admin@gmail.com", "admin123$", "ADMIN");
		MvcResult result3 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(user3)))
			.andExpect(status().is(201))
			.andReturn();
	}
	
	@Test
	void b_testFailedLoginAttempt() throws Exception{
		//wrong login attempt
		AuthRequest loginData = new AuthRequest("userOne", "wrongPassword");
		mockMvc.perform(post("/login").content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	void c_testSuccessLoginAttemptCLIENT() throws Exception{
		//user success login attempt
		AuthRequest loginData = new AuthRequest("clientOne", "client123$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		assert obj.has("accessToken");
		assert obj.getInt("status")==200;
		saveDataToFileSystem(TOKEN_CLIENT_1, obj.getString("accessToken"));
		
		AuthRequest loginData1 = new AuthRequest("clientTwo", "client456$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		JSONObject obj1 = new JSONObject(result.getResponse().getContentAsString());
		assert obj1.has("accessToken");
		assert obj1.getInt("status")==200;
		saveDataToFileSystem(TOKEN_CLIENT_2, obj.getString("accessToken"));
	}
	
	@Test
	void d_checkSuccessLoginAttemptADMIN() throws Exception{
		//Admin succes login attempt
		AuthRequest loginData =  AuthRequest("admin", "admin123$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		JSONObject jsonUser1Response = new JSONObject(result.getResponse().getContentAsString());
		assert jsonUser1Response.has("accessToken");
		assert jsonUser1Response.getInt("status")==200;
		saveDataToFileSystem(TOKEN_ADMIN_1, jsonUser1Response.getString("accessToken"));
	}
	
	@Test
	void f_checkSuccessTicketAdding1() throws Exception{
		//to add tickeyt1 successfully
		TicketModel ticketModel = new TicketModel(176345, "I'm locked out of my account. It says my account is suspended", "Account Suspension");
		MvcResult result = mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_CLIENT_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		print(result.getResponse().getContentAsString());
		
		//to check the ticket1 details
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("id");
		assert Objects.equals(response.getString("issue"), "!'m locked out of my account. It says my account is supended");
		assert Objects.equals(response.getString("category"), "Account Suspension");
		assert Objects.equals(response.getInt("ticketId"), 176345);
		
		saveDataToFileSystem(ID_TICKET_1,response.getInt("id"));
	}
	
	@Test
	void f_checkSuccessTicketAdding2() throws Exception{
		//to add tickeyt2 successfully
		TicketModel ticketModel = new TicketModel(176346, "My computer wont start up. It shows blue screen error.", "Hardware", "I have tried");
		MvcResult result2 = mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_CLIENT_2))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		print(result2.getResponse().getContentAsString());
		
		//to check the ticket2 details
		JSONObject response2 = new JSONObject(result2.getResponse().getContentAsString());
		assert response2.has("id");
		assert Objects.equals(response2.getString("createdAt"), getTime());
		assert Objects.equals(response2.getBoolean("priority"), true);
		assert Objects.equals(response2.getInt("clientId"), 2);
		
		saveDataToFileSystem(ID_TICKET_2,response2.getInt("id"));
	}
	
	@Test
	void h_checkFailedTicketAdding() throws Exception{
		TicketModel ticketModel = new TicketModel(176347, "I forgot my password and cant login to my account.", "Account Access", "Please assign");
		//check unauthorized access
		mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
		
		//check forbidden access
		mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	}

	@Test
	void i_getSuccessTicketCheckTest() throws Exception {
		//to get the ticket successfully using category
		mockMvc.perform(get("/ticket/list?category=Hardware").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		
	}
}
