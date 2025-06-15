package com.example.innovator24Dec;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.innovator24Dec.dto.AuthRequest;
import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;


//By default, JUnit runs tests using a deterministic but unpredictable order (MethodSorters.DEFAULT). In most cases,
//that behavior is perfectly fine and acceptable. But there are cases when we need to enforce a specific order.
//Test Methods Ordering in JUnit 5, we can use @TestMethodOrder to control the execution order of tests.
//JUnit 5 comes with a set of built-in MethodOrderer implementations to run tests in alphanumeric order. For example,
//it provides MethodOrderer.MethodName to sort test methods based on their names and their formal parameter lists.
//Similarly, we can use MethodOrderer.DisplayName to sort methods alphanumerically based on their display names.
//Please keep in mind that MethodOrderer.Alphanumeric is another alternative. However, this implementation 
//is deprecated and will be removed in 6.0.
//can use @Order(1)  @Order(2)  above test cases to define order
//@TestMethodOrder(MethodOrderer.Random.class) for random order test case execution
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class Innovator24DecApplicationTests {
//	@Test
//	void contextLoads() {	}
	
	private MockMvc mockMvc;
	
	public static final String TOKEN_DESIGNER_1 = "token_designer_1";
	public static final String TOKEN_DESIGNER_2 = "token_designer_2";
	public static final String TOKEN_USER_1 = "token_user_1";
	public static final String ID_DESIGN_1 = "id_design_1";
	public static final String ID_DESIGN_2 = "id_design_2";
	
	@Autowired
	WebApplicationContext context;
	
	//The @BeforeEach annotation in JUnit 5 is used to mark a method that should execute before each test method 
	//in a JUnit test case. It helps in providing initialization or setting up common test fixtures required by 
	//the test methods.
	@BeforeEach
	void setMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	private byte[] toJson(Object r) throws Exception{
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}
	
	private void print(String s) {
		System.out.println(s); //here in exam out.println(s) given but her out is giving error. need to import System
	}
	
	private void saveDataToFileSystem(Object key, Object value) throws Exception{
		try {
			//org.json used here or also present org.h2.util.json or net.minidev.json
			JSONObject jsonObject = new JSONObject();
			StringBuilder builder = new StringBuilder();
			try {
				//java.io used here or other also present
				File myObj = new File("temp.txt");
				Scanner myReader = new Scanner(myObj);
				while(myReader.hasNextLine()) {
					builder.append(myReader.nextLine());
				}
				myReader.close();
				if(!builder.toString().isEmpty()) {
					jsonObject = new JSONObject(builder.toString());
				}
			}
			catch (FileNotFoundException | JSONException e) {
				e.printStackTrace();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
			jsonObject.put((String) key, value);
			writer.write(jsonObject.toString());
			writer.close();
		}
		catch(JSONException | IOException e) {
			throw new Exception("Data not saved.");
		}
	}
	
	private Object getDataFromFileSystem(String key) throws Exception {
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
		}
		catch (FileNotFoundException | JSONException e) {
			throw new Exception("Data not found. Check authentication and ID generations to make sure data is being produced.");
		}
	}
	
	
	
	@Test
	void a_testSuccessLoginAttemptOfUsers_dataLoader() throws Exception{
		
		//User SuccessLoginAttemp with data stored in DB
		
		AuthRequest loginData1 = new AuthRequest("Sam", "pass3");
		MvcResult result1 = mockMvc.perform(post("/login")
				.content(toJson(loginData1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject jsonUser1Response1 = new JSONObject(result1.getResponse().getContentAsString());
		assert jsonUser1Response1.has("accessToken");
		assert jsonUser1Response1.getInt("status")==200;
		
		//Designer SuccessLoginAttemp with data stored in DB
		
		AuthRequest loginData2 = new AuthRequest("Dev", "pass1");
		MvcResult result2 = mockMvc.perform(post("/login")
				.content(toJson(loginData2)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj2 = new JSONObject(result2.getResponse().getContentAsString());
		assert obj2.has("accessToken");
		assert obj2.getInt("status")==200;
		
		AuthRequest loginData3 = new AuthRequest("John", "pass2");
		MvcResult result3 = mockMvc.perform(post("/login")
				.content(toJson(loginData3)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj3 = new JSONObject(result3.getResponse().getContentAsString());
		assert obj3.has("accessToken");
		assert obj3.getInt("status")==200;
	}
	
	@Test
	public void a_getDesignLoadData() throws Exception{
		//below input are given question paper for post ticket add api.. can verify from there if anything missing
		mockMvc.perform(get("/design/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].designId", Matchers.is(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].designName", containsStringIgnoringCase("Stylish Backpack")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", containsStringIgnoringCase("Durable and stylish backpack for everyday use.")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].color", containsStringIgnoringCase("Olive Green")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(990)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].availabilityStatus", containsStringIgnoringCase("In Stock")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].imageURL", containsStringIgnoringCase("http://example.com/images/backpack.jpg")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].availableSize", containsStringIgnoringCase("One Size")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].designerId", Matchers.is(1)))
		
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].designId", Matchers.is(2)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].designName", containsStringIgnoringCase("Casual Hoodie")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].description", containsStringIgnoringCase("Comfortable hoodie for a relaxed day out.")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].color", containsStringIgnoringCase("Black")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(2490)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].availabilityStatus", containsStringIgnoringCase("Out of Stock")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].imageURL", containsStringIgnoringCase("http://example.com/images/hoodie.jpg")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].availableSize", containsStringIgnoringCase("S, M, L, XL")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].designerId", Matchers.is(2)));
	}
	
	@Test
	void b_userAddTest() throws Exception{
		//below input are given question paper for post ticket add api.. can verify from there if anything missing
		UserInfo user1 = new UserInfo(4, "designerOne", "designer1@gmail.com", "designer123$", "DESIGNER");
		String userJson1 = "{ \"id\": 4, \"name\": \"designerOne\", \"email\": \"designer1@gmail.com\", \"password\": \"designer123$\", \"roles\": \"DESIGNER\" }";   
		System.out.println("Serialized JSON: " + userJson1);
		
		MvcResult result1 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson1))
				.andExpect(status().is(201))
				.andReturn();
		
		UserInfo user2 = new UserInfo(5, "designerTwo", "designer2@gmail.com", "designer456$", "DESIGNER");
		String userJson2 = "{ \"id\": 5, \"name\": \"designerTwo\", \"email\": \"designer2@gmail.com\", \"password\": \"designer456$\", \"roles\": \"DESIGNER\" }";   
		
		MvcResult result2 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson2))
				.andExpect(status().is(201))
				.andReturn();
		
		UserInfo user3 = new UserInfo(6, "userOne", "user1@gmail.com", "user123$", "USER");
		String userJson3 = "{ \"id\": 6, \"name\": \"userOne\", \"email\": \"user1@gmail.com\", \"password\": \"user123$\", \"roles\": \"USER\" }";   
		
		MvcResult result3 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson3))
				.andExpect(status().is(201))
				.andReturn();
	}
	
	@Test
	void c_testFailedLoginAttempt() throws Exception {
		
		//wrong login attempt
		
		AuthRequest loginData = new AuthRequest("designerOne", "wrong password");
		mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		//in log api if authentication mnager does not authenticare.. return bad request
	}
	
	@Test
	void d_testSuccessLoginAttemptDESIGNER() throws Exception{
		
		//DEsigner success login attempt
		
		AuthRequest loginData = new AuthRequest("designerOne", "designer123$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		assert obj.has("access_token");
		assert obj.getInt("status") == 200;
		saveDataToFileSystem(TOKEN_DESIGNER_1, obj.getString("accessToken"));
		
		AuthRequest loginData1 = new AuthRequest("designerTwo", "designer456$");
		MvcResult result1 = mockMvc.perform(post("/login")
				.content(toJson(loginData1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj1 = new JSONObject(result1.getResponse().getContentAsString());
		assert obj1.has("access_token");
		assert obj1.getInt("status") == 200;
		saveDataToFileSystem(TOKEN_DESIGNER_2, obj1.getString("accessToken"));
		
	}
	
	@Test
	void e_checkSuccessLoginAttemptUSER() throws Exception{
		
		//user success login attempt
		
		AuthRequest loginData = new AuthRequest("userOne", "user123$");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject jsonUser1Response = new JSONObject(result.getResponse().getContentAsString());
		assert jsonUser1Response.has("access_token");
		assert jsonUser1Response.getInt("status") == 200;
		saveDataToFileSystem(TOKEN_USER_1, jsonUser1Response.getString("accessToken"));
	}
	
	@Test
	void f_checkSuccessDesignAdding1() throws Exception{
		//check details from exam paper once, not given complete here
		
		//to add design1 successfully
		//String designName, String description, String color, int price, String availabilityStatus, String imageURL, String availableSize
		//"Floral Print Dress", "A beautiful floral print dress, perfect for summer outings and parties.", "Red", 2100, "In Stock", "http://example.com/images/dress", "One Size"  
		
		Design design = new Design(3, "Floral Print Dress", "A beautiful floral print dress, perfect for summer outings and parties.", "Red", 2100, "In Stock", "http://example.com/images/floral_dress.jpg", "S, M, L", 1);
		MvcResult result = mockMvc.perform(post("/design/add")
				.content(toJson(design))
				.header("Authorization", "Bearer " +getDataFromFileSystem(TOKEN_DESIGNER_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		print(result.getResponse().getContentAsString());
		
		//To check the design1 details
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("designId");
		//Objects present in io.jsowebtoken and java.utill, java.utill working here
		assert Objects.equals(response.getString("designName"), "Floral Print Dress");
		assert Objects.equals(response.getString("color"), "Red");
		
		saveDataToFileSystem(ID_DESIGN_1, response.getInt("designId"));
		
	}
	
	@Test
	void g_checkSuccessDesignAdding2() throws Exception{
		//check details from exam paper once, not given complete here
		
		//to add design2 successfully
				
		Design design = new Design(4, "Casual Sneakers", "Comfortable sneakers designed for everyday wear and casual outings.", "White", 2500, "Out Of Stock", "http://example.com/images/sneakers.jpg", "8,9,10", 2);
		MvcResult result = mockMvc.perform(post("/design/add")
				.content(toJson(design))
				.header("Authorization", "Bearer " +getDataFromFileSystem(TOKEN_DESIGNER_2))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();
		print(result.getResponse().getContentAsString());
		
		//To check the design2 details
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("designId");
		//Objects present in io.jsowebtoken and java.utill, java.utill working here
		assert Objects.equals(response.getString("designName"), "Casual Sneakers");
		assert Objects.equals(response.getString("color"), "White");
		
		saveDataToFileSystem(ID_DESIGN_2, response.getInt("designId"));
		
	}
	
	//check once this method.. not complete picture
	@Test
	void h_checkFailedDesignAdding() throws Exception{
		
		Design design = new Design(6, "Casual Sneakers", "Comfortable sneakers designed for everyday wear and casual outings.", "White", 2500, "In Stock", "http://example.com/images/sneakers.jpg", "8,9,10", 2);
		
		//check unauthorised access
		mockMvc.perform(post("/design/add")
				.content(toJson(design))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();

		//check forbidden access //only designer can add not the user so forbidden in security config
		mockMvc.perform(post("/design/add")
				.content(toJson(design))
				.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_USER_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	}
	
	
	@Test
	void i_updateDesignWithNoAccess() throws Exception{
		
		//json representation of the design data you want to update
		String designJson = "{"
				+"\"price\": \"1200\","
				+"\"avalabilityStatus\": \"In Stock\""
				+ "}" ;
		
		//Test case: update with wrong designer (should be forbidden)
		mockMvc.perform(put("/design/update/" + getDataFromFileSystem(ID_DESIGN_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_2))
				.content(designJson))//ensure you include the content here
		.andExpect(status().isForbidden())
		.andReturn();
		
		//Testcase: invalid designId (should return Bad Request)
		mockMvc.perform(put("/design/update/79")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_1))
				.content(designJson))//ensure you include the content here
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void j_updateDesignWithAccess() throws Exception{
		//json representation of the design data you want to update
		String designJson = "{"
				+"\"price\" : \"1200\","
				+"\"avalabilityStatus\" : \"In Stock\""
				+ "}" ;
		
		//Test case: only the designer who created design data can update it //Right Designer //Ok
		mockMvc.perform(put("/design/update/" + getDataFromFileSystem(ID_DESIGN_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_1))
				.content(designJson))//ensure you include the content here
		.andExpect(status().isOk())
		.andReturn();
	}
	
	@Test
	void k_deleteDeviceWithNoAccess() throws Exception{
		
		//only the designer who created can delete that particular design //wrong designer //forbidden
		mockMvc.perform(put("/design/delete/" + getDataFromFileSystem(ID_DESIGN_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_2)))
		.andExpect(status().is(403))
		.andReturn();
		
		//invalid design id //bad request
		mockMvc.perform(put("/design/delete/79")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_1)))
		.andExpect(status().is(400))
		.andReturn();
	}
	
	
	@Test
	void l_deleteDesignWithAccess() throws Exception{
		//only the designer who created can delete that particular design //correct designer //no content
		mockMvc.perform(put("/design/delete/" + getDataFromFileSystem(ID_DESIGN_2))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_2)))
		.andExpect(status().is(204))
		.andReturn();
	}
	
	
	@Test
	public void get_BadRequest_WithoutAccess() throws Exception{
		
		//wrong condition
		mockMvc.perform(put("/design/get/" + getDataFromFileSystem(ID_DESIGN_2))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_USER_1)))
		.andExpect(status().is(400))
		.andReturn();
		
		
		mockMvc.perform(put("/design/get/2")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_1)))
		.andExpect(status().is(400))
		.andReturn();
		
		//invalid design Id
		mockMvc.perform(put("/design/get/79")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_DESIGNER_1)))
		.andExpect(status().is(400))
		.andReturn();
		
		//unauthorised access //if Token not passed at all.. MyauthenticationentryPoint is used to send unauthorised
		mockMvc.perform(put("/design/get/1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
	}
	
	
	@Test
	public void get_OK_WithAcess() throws Exception{
		
		ResultActions result = mockMvc.perform(get("/design/get/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSystem(TOKEN_USER_1)))
				.andExpect(status().is(200))
				
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].designId", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].designName", containsStringIgnoringCase("Stylish Backpack")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", containsStringIgnoringCase("Durable and stylish backpack for everyday use.")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].color", containsStringIgnoringCase("Olive Green")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(990)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].availabilityStatus", containsStringIgnoringCase("In Stock")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].imageURL", containsStringIgnoringCase("http://example.com/images/backpack.jpg")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].availableSize", containsStringIgnoringCase("One Size")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].designerId", Matchers.is(1)));
		
		String responseContent = result.andReturn().getResponse().getContentAsString();
		System.out.println("Response: " + responseContent);
	
	}
	
	
	@Test
	public void get_BadRequest_DesignDetailsUsingPrice() throws Exception{
		
		//invalid or wrong price value
		mockMvc.perform(put("/design/filter").param("price", String.valueOf(4000)))
		.andExpect(status().isBadRequest())
		.andReturn();
		
		mockMvc.perform(put("/design/filter").param("price", String.valueOf(1234)))
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
	@Test
	public void get_OK_DesignDetailsUsingPrice() throws Exception{
		
		mockMvc.perform(get("/design/filter").param("price", String.valueOf(990))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].designId", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].designName", containsStringIgnoringCase("Stylish Backpack")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", containsStringIgnoringCase("Durable and stylish backpack for everyday use.")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].color", containsStringIgnoringCase("Olive Green")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(990)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].availabilityStatus", containsStringIgnoringCase("In Stock")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].imageURL", containsStringIgnoringCase("http://example.com/images/backpack.jpg")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].availableSize", containsStringIgnoringCase("One Size")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].designerId", Matchers.is(1)));
	}
	
	
	@Test
	public void getDesignData() throws Exception{
		mockMvc.perform(get("design/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].designName", containsStringIgnoringCase("Stylish Backpack")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", containsStringIgnoringCase("Durable and stylish backpack for everyday use.")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].color", containsStringIgnoringCase("Olive Green")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(990)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].availabilityStatus", containsStringIgnoringCase("In Stock")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].imageURL", containsStringIgnoringCase("http://example.com/images/backpack.jpg")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].availableSize", containsStringIgnoringCase("One Size")))
		
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].designName", containsStringIgnoringCase("Casual Hoodie")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].description", containsStringIgnoringCase("Comfortable hoodie for a relaxed day out.")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].color", containsStringIgnoringCase("Black")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(2490)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].availabilityStatus", containsStringIgnoringCase("Out of Stock")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].imageURL", containsStringIgnoringCase("http://example.com/images/hoodie.jpg")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].availableSize", containsStringIgnoringCase("S, M, L, XL")))
		
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].designName", containsStringIgnoringCase("Floral Print Dress")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].description", containsStringIgnoringCase("A beautiful floral print dress, perfect for summer outings and parties.")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].color", containsStringIgnoringCase("Red")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].price", Matchers.is(2100)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].availabilityStatus", containsStringIgnoringCase("In Stock")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].imageURL", containsStringIgnoringCase("http://example.com/images/floral_dress.jpg")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].availableSize", containsStringIgnoringCase("S,M,L")))
		
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].designName", containsStringIgnoringCase("Casual Sneakers")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].description", containsStringIgnoringCase("Comfortable sneakers designed for everyday wear and casual outings.")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].color", containsStringIgnoringCase("White")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].price", Matchers.is(2500)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].availabilityStatus", containsStringIgnoringCase("Out of Stock")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].imageURL", containsStringIgnoringCase("http://example.com/images/sneakers.jpg")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[3].availableSize", containsStringIgnoringCase("8,9,10")));
		
	}
	
}
