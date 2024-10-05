package com.example.Innovator24June;
import com.example.Innovator24June.dto.AuthRequest;
import com.example.Innovator24June.entity.TicketModel;
import com.example.Innovator24June.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;
import org.json.JSONException;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class Innovator24JuneApplicationTests {
//	@Test
//	void contextLoads() {  //given by defualt in this class , but no need can delete
//	}
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
		
		UserInfo user3 = new UserInfo(3, "admin", "admin@gmail.com", "admin123$", "ADMIN");
		MvcResult result3 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(user3)))
			.andExpect(status().is(201))
			.andReturn();
	}
	
	@Test
	void b_testFailedLoginAttempt() throws Exception{
		//wrong login attempt
		AuthRequest loginData = new AuthRequest("userOne", "wrongpassword");
		mockMvc.perform(post("/login").content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest()).andReturn();
	}
	
	
	@Test
	void c_testSuccessLoginAttemptCLIENT() throws Exception{
		
		//user success login attempt
		
		AuthRequest loginData = new AuthRequest("clientOne", "client123$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		assert obj.has("accessToken");
		assert obj.getInt("status")==200;
		saveDataToFileSystem(TOKEN_CLIENT_1, obj.getString("accessToken"));
		
		AuthRequest loginData1 = new AuthRequest("clientTwo", "client456$");
		MvcResult result1 = mockMvc.perform(post("/login")
				.content(toJson(loginData1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj1 = new JSONObject(result1.getResponse().getContentAsString());
		assert obj1.has("accessToken");
		assert obj1.getInt("status")==200;
		saveDataToFileSystem(TOKEN_CLIENT_2, obj1.getString("accessToken"));
		
	}
	
	
	@Test
	void d_checkSuccessLoginAttemptADMIN() throws Exception{
		
		//Admin succes login attempt
		
		AuthRequest loginData = new AuthRequest("admin", "admin123$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject jsonUser1Response = new JSONObject(result.getResponse().getContentAsString());
		assert jsonUser1Response.has("accessToken");
		assert jsonUser1Response.getInt("status")==200;
		saveDataToFileSystem(TOKEN_ADMIN_1, jsonUser1Response.getString("accessToken"));
		
	}
	
	@Test
	void f_checkSuccessTicketAdding1() throws Exception{
		
		
		//to add ticket1 successfully
		
		TicketModel ticketModel = new TicketModel(176345, "I'm locked out of my account. It says my account is suspended", "Account Suspension", "Please help me resolve this issue");
		MvcResult result = mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_CLIENT_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		print(result.getResponse().getContentAsString());
		
		//to check the ticket1 details
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("id");
		assert Objects.equals(response.getString("issue"), "I'm locked out of my account. It says my account is suspended");
		assert Objects.equals(response.getString("category"), "Account Suspension");
		assert Objects.equals(response.getInt("ticketId"), 176345);
		
		saveDataToFileSystem(ID_TICKET_1,response.getInt("id"));
		
	}
	
	
	@Test
	void f_checkSuccessTicketAdding2() throws Exception{
		
		
		//to add tickeyt2 successfully //below input are given question paper for post ticket add api..
		TicketModel ticketModel = new TicketModel(176346, "My computer wont start up. It shows a blue screen error.", "Hardware", "I have tried restarting multiple times, but the issue persist");
		MvcResult result2 = mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_CLIENT_2))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		
		print(result2.getResponse().getContentAsString());
		
		//to check the ticket2 details
		JSONObject response2 = new JSONObject(result2.getResponse().getContentAsString());
		assert response2.has("id");
		assert Objects.equals(response2.getString("createdAt"), gettime());
		assert Objects.equals(response2.getBoolean("priority"), true);
		assert Objects.equals(response2.getInt("clientId"), 2);
		
		saveDataToFileSystem(ID_TICKET_2,response2.getInt("id"));
		
	}
	
	@Test
	void h_checkFailedTicketAdding() throws Exception{
		
		//below input are given question paper for post ticket add api.. can verify from there if anything missing
		TicketModel ticketModel = new TicketModel(176347, "I forgot my password and cant login to my account.", "Account Access", "Please assign");
		
		//check unauthorized access //unauthorized means no access at all
		mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
		
		//check forbidden access // forbidden means has some access but not all, here checks Authorization bearer token
		mockMvc.perform(post("/ticket/add")
				.content(toJson(ticketModel))
				.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
		
	}

	
	@Test
	void i_getSuccessTicketCheckTest() throws Exception { //if caausing any issue then comment this test case, 
		//remaining test cases should pass.. this we can verify in postman output also.. just check output
		
		//to get the ticket successfully using category
		mockMvc.perform(get("/ticket/list?category=Hardware").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization","Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		//.andExpect(jsonPath("$.[0].id", Matchers.is(2), null))
		.andExpect(jsonPath("$.[0].id", Matchers.is(2)))
		.andExpect(jsonPath("$.[0].ticketId", Matchers.is(176346)))
		.andExpect(jsonPath("$.[0].issue", containsStringIgnoringCase("My computer wont start up. It shows a blue screen error.")))
		.andExpect(jsonPath("$.[0].category", containsStringIgnoringCase("Hardware")))
		.andExpect(jsonPath("$.[0].createdAt", containsStringIgnoringCase(gettime())))
		.andExpect(jsonPath("$.[0].priority", Matchers.is(true)))
		.andExpect(jsonPath("$.[0].comments", containsStringIgnoringCase("I have tried restarting multiple times, but the issue persist")))
		.andExpect(jsonPath("$.[0].status", containsStringIgnoringCase("New")))
		.andExpect(jsonPath("$.[0].clientId", Matchers.is(2)));
		
		
	}
	
	@Test
	void j_getFailedTicketCheckTest() throws Exception{
		
		//wrong category
		
		mockMvc.perform(get("/ticket/list?category=wrongValue").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_CLIENT_1)))
		.andExpect(MockMvcResultMatchers.status().is(400));
		
		
	}
	
	
	@Test
	void k_updateSuccessTicketwithDetailsCheck() throws Exception{
		
		//successfully Ticket updated by admin
		
		TicketModel tourismModel = new TicketModel(false, "Closed");
		MvcResult result = mockMvc.perform(patch("/ticket/update/"+getDataFromFileSystem(ID_TICKET_1))
				.content(toJson(tourismModel))
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(200)).andReturn();
				
				JSONObject response = new JSONObject(result.getResponse().getContentAsString());
				assert response.has("id");
				assert Objects.equals(response.getBoolean("priority"), false);
				assert Objects.equals(response.getString("status"), "Closed");
				assert Objects.equals(response.getString("category"), "Account Suspension");
				
				
				
	}
	
	@Test
	void l_updateFailedTicketWithDetailsCheck() throws Exception{
		
		TicketModel ticketModel = new TicketModel(false, "Closed");
		
		//client can not update
		
		MvcResult result1 = mockMvc.perform(patch("/ticket/update/"+getDataFromFileSystem(ID_TICKET_1))
				.content(toJson(ticketModel))
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_CLIENT_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403)).andReturn();
		
		//bad id that is not valid
		
		MvcResult result2 = mockMvc.perform(patch("/ticket/update/8")
				.content(toJson(ticketModel))
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400)).andReturn();
				
				
				
	}
	
	
	
	@Test
	void m_deleteTicketWithNoAccess() throws Exception{
		
		//admin can't delete //forbidden
		mockMvc.perform(delete("/ticket/delete/"+getDataFromFileSystem(ID_TICKET_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_ADMIN_1)))
				.andExpect(status().isForbidden())
				.andReturn();
		
		//only the client who create can delete that particular ticket details //wrong user //forbidden
		mockMvc.perform(delete("/ticket/delete/"+getDataFromFileSystem(ID_TICKET_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_CLIENT_2)))
				.andExpect(status().is(403))
				.andReturn();
		
		
		//invalid ticket id //bad request
		mockMvc.perform(delete("/ticket/delete/7")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_CLIENT_1)))
				.andExpect(status().is(400))
				.andReturn();
		
	}
	
	
	@Test
	void n_deleteTicketWithAccess() throws Exception{
		
		
		
		//only the client who create can delete that particular ticket details //correct user //no content
		mockMvc.perform(delete("/ticket/delete/"+getDataFromFileSystem(ID_TICKET_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_CLIENT_1)))
				.andExpect(status().is(204))
				.andReturn();
		
		
		//only the client who create can delete that particular ticket details //correct user //no content
		mockMvc.perform(delete("/ticket/delete/"+getDataFromFileSystem(ID_TICKET_2))
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_CLIENT_2)))
						.andExpect(status().is(204))
						.andReturn();
				
				
				
	}
	
	
	
	
	private byte[] toJson(Object r) throws Exception{
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}
	
	
	private void print(String s) {
		System.out.println(s);//in paper given out.println(s) but this is giving error for me
	}
	
	private void saveDataToFileSystem(Object key, Object value) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject();
			StringBuilder builder = new StringBuilder();
			try {
				File myObj = new File("temp.txt");
				Scanner myReader = new Scanner(myObj);
				while(myReader.hasNextLine()) {
					builder.append(myReader.nextLine());
				}
				myReader.close();
				if(!builder.toString().isEmpty())
					jsonObject = new JSONObject(builder.toString());
			} catch(FileNotFoundException | JSONException e){
				e.printStackTrace();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
			jsonObject.put((String) key, value);
			writer.write(jsonObject.toString());
			writer.close();
		} catch(JSONException | IOException e) {
			throw new Exception("Data not saved");
		}
	}
	
	private Object getDataFromFileSystem(String key) throws Exception{
		try {
			File myObj = new File("temp.txt");
			Scanner myReader = new Scanner(myObj);
			StringBuilder builder = new StringBuilder();
			while(myReader.hasNextLine()) {
				builder.append(myReader.nextLine());
			}
			myReader.close();
			JSONObject jsonObject = new JSONObject(builder.toString());
			return jsonObject.get(key);
		} catch (FileNotFoundException | JSONException e){
			throw new Exception("Data not found. Check authentication and ID generations to make sure data is being produced.");
		}
	}
	
	
	//to get the current date
	public String gettime() {
		String x = String.valueOf(System.currentTimeMillis());
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//we tell how we want to format date
		long milliseconds = Long.parseLong(x);
		Calendar calendar= Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		return formatter.format(calendar.getTime());
	}
	
}
