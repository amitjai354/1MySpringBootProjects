package com.example.Innovator2025June28;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.Innovator2025June28.dto.AuthRequest;
import com.example.Innovator2025June28.entity.Show;
import com.example.Innovator2025June28.entity.Station;
import com.example.Innovator2025June28.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class Innovator2025June28ApplicationTests {

	
	private MockMvc mockMvc;
	
	//these values are set later
	public static final String TOKEN_ADMIN_1 = "token_admin_1";//this is used as key to save token for admin
	public static final String TOKEN_ADMIN_2 = "token_admin_2";
	public static final String TOKEN_USER_1 = "token_user_1";
	public static final String ID_STATION_1 = "id_station_1";//these values are set later
	public static final String ID_STATION_2 = "id_station_2";
	public static final String ID_SHOW_1 = "id_show_1";
	
	@Autowired
	WebApplicationContext context;
	
	@BeforeEach
	void setMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	//this test data added in data loader, but just after security config code it will not run, need to write findAll and all annoattions in controller class
	@Test
	public void a_getStationData() throws Exception{
		mockMvc.perform(get("/station/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(200))
		.andExpect(jsonPath("&[0].station_id", Matchers.is(1)))
		.andExpect(jsonPath("&[0].name", Matchers.equalToIgnoringCase("RadioWave FM")))
		.andExpect(jsonPath("&[0].frequency", Matchers.equalToIgnoringCase("101.2 FM")))
		.andExpect(jsonPath("&[0].genre", Matchers.equalToIgnoringCase("Pop")))
		.andExpect(jsonPath("&[0].language", Matchers.equalToIgnoringCase("English")))
		.andExpect(jsonPath("&[0].country", Matchers.equalToIgnoringCase("USA")))
		.andExpect(jsonPath("&[0].streamingURL", Matchers.equalToIgnoringCase("https://radiowavefm.com/stream")))
		.andExpect(jsonPath("&[0].live", Matchers.is(true)))
		.andExpect(jsonPath("&[0].startTime", Matchers.equalToIgnoringCase("06:00 AM")))
		.andExpect(jsonPath("&[0].endTime", Matchers.equalToIgnoringCase("11:00 PM")))
		.andExpect(jsonPath("&[0].operatorId", Matchers.is(1)))
		
		.andExpect(jsonPath("&[1].station_id", Matchers.is(2)))
		.andExpect(jsonPath("&[1].name", Matchers.equalToIgnoringCase("Global Beats")))
		.andExpect(jsonPath("&[1].frequency", Matchers.equalToIgnoringCase("101.3 FM")))
		.andExpect(jsonPath("&[1].genre", Matchers.equalToIgnoringCase("Western")))
		.andExpect(jsonPath("&[1].language", Matchers.equalToIgnoringCase("French")))
		.andExpect(jsonPath("&[1].country", Matchers.equalToIgnoringCase("France")))
		.andExpect(jsonPath("&[1].streamingURL", Matchers.equalToIgnoringCase("https://globalbeates.fr/stream")))
		.andExpect(jsonPath("&[1].live", Matchers.is(false)))
		.andExpect(jsonPath("&[1].startTime", Matchers.equalToIgnoringCase("05:00 AM")))
		.andExpect(jsonPath("&[1].endTime", Matchers.equalToIgnoringCase("10:00 PM")))
		.andExpect(jsonPath("&[1].operatorId", Matchers.is(2)));
	}
	
	@Test
	public void a_getShowData() throws Exception{
		mockMvc.perform(get("/show/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(200))
		
		.andExpect(jsonPath("&[0].showId", Matchers.is(1)))
		.andExpect(jsonPath("&[0].title", Matchers.equalToIgnoringCase("Morning Vibes")))
		.andExpect(jsonPath("&[0].description", Matchers.containsStringIgnoringCase("refreshing morning show with great music and positive talks")))
		.andExpect(jsonPath("&[0].showTime", Matchers.equalToIgnoringCase("08:00 AM - 10:00 AM")))
		.andExpect(jsonPath("&[0].category", Matchers.equalToIgnoringCase("Music & Talk")))
		.andExpect(jsonPath("&[0].host", Matchers.equalToIgnoringCase("Emma Roberts")))
		.andExpect(jsonPath("&[0].duration", Matchers.is(120)))
		.andExpect(jsonPath("&[0].popularityRating", Matchers.is(5)))
		.andExpect(jsonPath("&[0].station_id", Matchers.is(1)));
		
		
	}
	
	
	@Test
	void a_AddingUserInfo() throws Exception{
		
		//takes normal password in input and in signUp api, it encodes the password first then saves user
		UserInfo user1 = new UserInfo("Dev", "dev@gmail.com", "pass1", "ADMIN");
		String userJson1 = "{ \"name\": \"Dev\", \"email\": \"dev@gmail.com\", \"password\": \"pass1\", \"roles\": \"ADMIN\" }";
		MvcResult result1 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson1))
				.andExpect(status().is(201))
				.andReturn();
		
		UserInfo user2 = new UserInfo("Justin", "justin@gmail.com", "pass2", "ADMIN");
		String userJson2 = "{ \"name\": \"Justin\", \"email\": \"justin@gmail.com\", \"password\": \"pass2\", \"roles\": \"ADMIN\" }";
		MvcResult result2 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson2))
				.andExpect(status().is(201))
				.andReturn();
		
		UserInfo user3 = new UserInfo("Sam", "sam@gmail.com", "pass3", "USER");
		String userJson3 = "{ \"name\": \"Sam\", \"email\": \"sam@gmail.com\", \"password\": \"pass3\", \"roles\": \"USER\" }";
		MvcResult result3 = mockMvc.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson3))
				.andExpect(status().is(201))
				.andReturn();
	}
	
	@Test
	void b_testFailedLoginAttempt() throws Exception{
		
		//wrong login attempt
		AuthRequest loginData = new AuthRequest("adminOne", "wrong password");
		mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	void c_testSuccessLoginAttemptADMIN() throws Exception{
		
		//ADMIN success login attempt
		
		AuthRequest loginData = new AuthRequest("Dev", "pass1");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		assert obj.has("accessToken");//here got token from login api in response
		assert obj.getInt("status")==200;
		saveDataToFileSystem(TOKEN_ADMIN_1, obj.getString("accessToken"));
		//here saving token to temp.txt for ADMIN, here key is TOKEN_ADMIN_1 and value is accessToken that we got from login api
		
		AuthRequest loginData1 = new AuthRequest("Justin", "pass2");
		MvcResult result1 = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject obj1 = new JSONObject(result.getResponse().getContentAsString());
		assert obj1.has("accessToken");//here got token from login api in response
		assert obj1.getInt("status")==200;
		saveDataToFileSystem(TOKEN_ADMIN_2, obj1.getString("accessToken"));
		
	}
	
	@Test
	void d_checkSuccessLoginAttemptUSER() throws Exception{
		
		//USER success login attempt
		
		AuthRequest loginData = new AuthRequest("Sam", "pass3");
		MvcResult result = mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		JSONObject jsonUser1Response = new JSONObject(result.getResponse().getContentAsString());
		assert jsonUser1Response.has("accessToken");//here got token from login api in response
		assert jsonUser1Response.getInt("status")==200;
		saveDataToFileSystem(TOKEN_USER_1, jsonUser1Response.getString("accessToken"));
		
	}
	
	@Test
	void e_checkSuccessStationAdding1() throws Exception{
		
		//To add station data successfully
		//String name, String frequency, String genre, String language, String country, String streamingURL, boolean isLive, String 
		//startTime, String endTime, int operatorId
		
		Station station = new Station("BBC Radio 1", "97.99 FM", "Contemporary Hit Radio", "English", "United Kingdom", "https://www.bbc.co.uk/sounds/play/live:bbc_radio_one", true, "00:00", "23:59", 1);
		MvcResult result = mockMvc.perform(post("/station/add")
				.content(toJson(station))//here passing RequestBody station as content
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_1))
				//here passing header, Authorization : Bearer tokenForAdmin1_That_We_saved_above_to_temp.txt_after_success_login
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();//here got response
		print(result.getResponse().getContentAsString());
		
		//to check the station details //here checking response
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("station_id");
		assert Objects.equals(response.getString("genre"), "Contemporary Hit Radio");
		assert Objects.equals(response.getString("language"), "English");
		assert Objects.equals(response.getString("operatorId"), 1);
		
		saveDataToFileSystem(ID_STATION_1, response.getInt("station_id"));//saving station id to temp.txt
		
	}
	
	@Test
	void f_checkSuccessStationAdding2() throws Exception{
		//To add station data successfully
		//String name, String frequency, String genre, String language, String country, String streamingURL, boolean isLive, String 
		//startTime, String endTime, int operatorId
		
		Station station = new Station("NPR News", "89.1 FM", "News/Talk", "English", "United States", "https://www.npr.org/live", true, "00:00", "23:59", 2);
		MvcResult result = mockMvc.perform(post("/station/add")
				.content(toJson(station))//here passing RequestBody station as content
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_2))
				//here passing header, Authorization : Bearer tokenForAdmin1_That_We_saved_above_to_temp.txt_after_success_login
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();//here got response
		print(result.getResponse().getContentAsString());
		
		//to check the station details //here checking response
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("station_id");
		assert Objects.equals(response.getString("genre"), "News/Talk");
		assert Objects.equals(response.getString("language"), "English");
		assert Objects.equals(response.getString("operatorId"), 2);
		
		saveDataToFileSystem(ID_STATION_2, response.getInt("station_id"));//saving station id to temp.txt
		
	}
	
	@Test
	void g_checkSuccessShowAdding1() throws Exception{
		
		//To add show data successfully
		//String title, String description, String showTime, int duration, String host, String category, int popularityRating, int 
		//station_id
		
		Show show = new Show("Evening Chill out Session", "Unwind with soothing tunes and relaxing conversations to end your day.", "08:00 PM - 10:00 PM", 120, "Emily Davis", "Music & Talk", 4, 2);
		MvcResult result = mockMvc.perform(post("/show/add")
				.content(toJson(show))
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201)).andReturn();//here got response
		print(result.getResponse().getContentAsString());
		
		//To check the show data
		JSONObject response = new JSONObject(result.getResponse().getContentAsString());
		assert response.has("showId");
		assert Objects.equals(response.getString("title"), "Evening Chill out Session");
		assert Objects.equals(response.getString("showTime"), "08:00 PM - 10:00 PM");

		saveDataToFileSystem(ID_SHOW_1, response.getInt("showId"));
		
	}
	
	
	@Test
	void h_checkFailedStation_ShowAdding() throws Exception{
		
		Station station = new Station("NPR News", "89.1 FM", "News/Talk", "English", "United States", "https://www.npr.org/live", true, "00:00", "23:59", 2);
	
		Show show = new Show("Evening Chill out Session", "Unwind with soothing tunes and relaxing conversations to end your day.", "08:00 PM - 10:00 PM", 120, "Emily Davis", "Music & Talk", 4, 2);

		//check unauthorized access
		mockMvc.perform(post("/station/add")
				.content(toJson(station))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
		
		//check unauthorized access
		mockMvc.perform(post("/show/add")
				.content(toJson(show))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
		
		//check forbidden access
		mockMvc.perform(post("/station/add")
				.content(toJson(station))
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_USER_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
				
		//check forbidden access
		mockMvc.perform(post("/show/add")
				.content(toJson(show))
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_USER_1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
		
	}
	
	
	//this one we test after we have added some data in the db by post api
	@Test
	public void i_getAllStationData() throws Exception{
		mockMvc.perform(get("/station/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(200))
				.andExpect(jsonPath("&[0].station_id", Matchers.is(1)))
				.andExpect(jsonPath("&[0].name", Matchers.equalToIgnoringCase("RadioWave FM")))
				.andExpect(jsonPath("&[0].frequency", Matchers.equalToIgnoringCase("101.2 FM")))
				.andExpect(jsonPath("&[0].genre", Matchers.equalToIgnoringCase("Pop")))
				.andExpect(jsonPath("&[0].language", Matchers.equalToIgnoringCase("English")))
				.andExpect(jsonPath("&[0].country", Matchers.equalToIgnoringCase("USA")))
				.andExpect(jsonPath("&[0].streamingURL", Matchers.equalToIgnoringCase("https://radiowavefm.com/stream")))
				.andExpect(jsonPath("&[0].live", Matchers.is(true)))
				.andExpect(jsonPath("&[0].startTime", Matchers.equalToIgnoringCase("06:00 AM")))
				.andExpect(jsonPath("&[0].endTime", Matchers.equalToIgnoringCase("11:00 PM")))
				.andExpect(jsonPath("&[0].operatorId", Matchers.is(1)))
				
				.andExpect(jsonPath("&[1].station_id", Matchers.is(2)))
				.andExpect(jsonPath("&[1].name", Matchers.equalToIgnoringCase("Global Beats")))
				.andExpect(jsonPath("&[1].frequency", Matchers.equalToIgnoringCase("101.3 FM")))
				.andExpect(jsonPath("&[1].genre", Matchers.equalToIgnoringCase("Western")))
				.andExpect(jsonPath("&[1].language", Matchers.equalToIgnoringCase("French")))
				.andExpect(jsonPath("&[1].country", Matchers.equalToIgnoringCase("France")))
				.andExpect(jsonPath("&[1].streamingURL", Matchers.equalToIgnoringCase("https://globalbeates.fr/stream")))
				.andExpect(jsonPath("&[1].live", Matchers.is(false)))
				.andExpect(jsonPath("&[1].startTime", Matchers.equalToIgnoringCase("05:00 AM")))
				.andExpect(jsonPath("&[1].endTime", Matchers.equalToIgnoringCase("10:00 PM")))
				.andExpect(jsonPath("&[1].operatorId", Matchers.is(2)))
				
				.andExpect(jsonPath("&[2].station_id", Matchers.is(3)))
				.andExpect(jsonPath("&[2].name", Matchers.equalToIgnoringCase("BBC Radio 1")))
				.andExpect(jsonPath("&[2].frequency", Matchers.equalToIgnoringCase("97.99 FM")))
				.andExpect(jsonPath("&[2].genre", Matchers.equalToIgnoringCase("Contemporary Hit Radio")))
				.andExpect(jsonPath("&[2].language", Matchers.equalToIgnoringCase("English")))
				.andExpect(jsonPath("&[2].country", Matchers.equalToIgnoringCase("United Kingdom")))
				.andExpect(jsonPath("&[2].streamingURL", Matchers.equalToIgnoringCase("https://www.bbc.co.uk/sounds/play/live:bbc_radio_one")))
				.andExpect(jsonPath("&[2].live", Matchers.is(true)))
				.andExpect(jsonPath("&[2].startTime", Matchers.equalToIgnoringCase("00:00")))
				.andExpect(jsonPath("&[2].endTime", Matchers.equalToIgnoringCase("23:59")))
				.andExpect(jsonPath("&[2].operatorId", Matchers.is(1)))
				
				
				.andExpect(jsonPath("&[3].station_id", Matchers.is(4)))
				.andExpect(jsonPath("&[3].name", Matchers.equalToIgnoringCase("NPR News")))
				.andExpect(jsonPath("&[3].frequency", Matchers.equalToIgnoringCase("89.1 FM")))
				.andExpect(jsonPath("&[3].genre", Matchers.equalToIgnoringCase("News/Talk")))
				.andExpect(jsonPath("&[3].language", Matchers.equalToIgnoringCase("English")))
				.andExpect(jsonPath("&[3].country", Matchers.equalToIgnoringCase("United States")))
				.andExpect(jsonPath("&[3].streamingURL", Matchers.equalToIgnoringCase("https://www.npr.org/live")))
				.andExpect(jsonPath("&[3].live", Matchers.is(true)))
				.andExpect(jsonPath("&[3].startTime", Matchers.equalToIgnoringCase("00:00")))
				.andExpect(jsonPath("&[3].endTime", Matchers.equalToIgnoringCase("23:59")))
				.andExpect(jsonPath("&[3].operatorId", Matchers.is(2)));
		
	}
	
	@Test
	public void j_getAllShowData() throws Exception{
		mockMvc.perform(get("/show/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(200))
				
				.andExpect(jsonPath("&[0].showId", Matchers.is(1)))
				.andExpect(jsonPath("&[0].title", Matchers.equalToIgnoringCase("Morning Vibes")))
				.andExpect(jsonPath("&[0].description", Matchers.containsStringIgnoringCase("refreshing morning show with great music and positive talks")))
				.andExpect(jsonPath("&[0].showTime", Matchers.equalToIgnoringCase("08:00 AM - 10:00 AM")))
				.andExpect(jsonPath("&[0].category", Matchers.equalToIgnoringCase("Music & Talk")))
				.andExpect(jsonPath("&[0].host", Matchers.equalToIgnoringCase("Emma Roberts")))
				.andExpect(jsonPath("&[0].duration", Matchers.is(120)))
				.andExpect(jsonPath("&[0].popularityRating", Matchers.is(5)))
				.andExpect(jsonPath("&[0].station_id", Matchers.is(1)))
		
				.andExpect(jsonPath("&[1].showId", Matchers.is(2)))
				.andExpect(jsonPath("&[1].title", Matchers.equalToIgnoringCase("Evening Chill out Session")))
				.andExpect(jsonPath("&[1].description", Matchers.containsStringIgnoringCase("Unwind with soothing tunes and relaxing conversations to end your day.")))
				.andExpect(jsonPath("&[1].showTime", Matchers.equalToIgnoringCase("08:00 PM - 10:00 PM")))
				.andExpect(jsonPath("&[1].category", Matchers.equalToIgnoringCase("Music & Talk")))
				.andExpect(jsonPath("&[1].host", Matchers.equalToIgnoringCase("Emily Davis")))
				.andExpect(jsonPath("&[1].duration", Matchers.is(120)))
				.andExpect(jsonPath("&[1].popularityRating", Matchers.is(4)))
				.andExpect(jsonPath("&[1].station_id", Matchers.is(2)));
	}
	
	@Test
	void k_updateStationDataWithNoAccess() throws Exception {
		//Json representation of the station data you want to update
		String stationJson = "{"
				
				+"\"frequency\": \"101 FM\","
				+"\"country\": \"India\""
				+"}";
		
		//Test case update with wrong ADMIN (should be forbidden)
		//here trying to update station 1 by admin 2 so forbidden as admin 1 created this
		mockMvc.perform(put("/station/update/" + getDataFromFileSyatem(ID_STATION_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_2))
				.content(stationJson))
		.andExpect(status().isForbidden())
		.andReturn();
		
		mockMvc.perform(put("/station/update/" + getDataFromFileSyatem(ID_STATION_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_USER_1))
				.content(stationJson))
		.andExpect(status().isForbidden())
		.andReturn();
		
		//invalid station id, should return bad request as no data found
		mockMvc.perform(put("/station/update/9")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_1))
				.content(stationJson))
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void l_updateStationWithAccess() throws Exception{
		
		//Json representation of station data you want to update
		String stationJson = "{"
				
				+"\"frequency\": \"101 FM\","
				+"\"country\": \"India\""
				+"}";
		
		mockMvc.perform(put("/station/update/" + getDataFromFileSyatem(ID_STATION_1))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_1))
				.content(stationJson))
		.andExpect(status().isOk())
		.andReturn();
	}
	
	@Test
	public void get_BadREquest_ShowDetailsUsingShowTime() throws Exception{
		
		//check unauthorized access
		mockMvc.perform(get("/show/get/airing")
				.param("showTime", "08:00 AM - 10:00 AM"))
		.andExpect(status().isUnauthorized()).andReturn();
		
		//check unauthorized access
		mockMvc.perform(get("/show/get/airing")
				.param("showTime", "08:00 AM - 10:00 AM")
		.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_1)))
		.andExpect(status().isForbidden()).andReturn();
		
		//invalid show time, bad request no data found
		mockMvc.perform(get("/show/get/airing")
				.param("showTime", "08:00 AM - 11:00 AM")
		.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_USER_1)))
		.andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void get_OK_ShowDetailUsingShowTimw() throws Exception{
		mockMvc.perform(get("/show/get/airing")
				.param("showTime", "08:00 AM - 10:00 AM")
		.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_USER_1)))
		.andExpect(status().isOk())
		
		.andExpect(jsonPath("&[0].showId", Matchers.is(1)))
		.andExpect(jsonPath("&[0].title", Matchers.equalToIgnoringCase("Morning Vibes")))
		.andExpect(jsonPath("&[0].description", Matchers.containsStringIgnoringCase("refreshing morning show with great music and positive talks")))
		.andExpect(jsonPath("&[0].showTime", Matchers.equalToIgnoringCase("08:00 AM - 10:00 AM")))
		.andExpect(jsonPath("&[0].category", Matchers.equalToIgnoringCase("Music & Talk")))
		.andExpect(jsonPath("&[0].host", Matchers.equalToIgnoringCase("Emma Roberts")))
		.andExpect(jsonPath("&[0].duration", Matchers.is(120)))
		.andExpect(jsonPath("&[0].popularityRating", Matchers.is(5)))
		.andExpect(jsonPath("&[0].station_id", Matchers.is(1)))
		
		.andReturn();
	}
	
	@Test
	public void getPopularShow_BadRequest() throws Exception{
		//unauthorized
		mockMvc.perform(get("/show/popularShow"))
		.andExpect(status().isUnauthorized())
		.andReturn();
		
		//forbidden access to admins
		mockMvc.perform(get("/show/popularShow")
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_ADMIN_1)))
		.andExpect(status().isForbidden())
		.andReturn();
	}
	
	@Test
	public void getPopularShow_Success() throws Exception{
		
		mockMvc.perform(get("/show/popularShow")
				.header("Authorization", "Bearer " + getDataFromFileSyatem(TOKEN_USER_1)))
		.andExpect(status().isOk())
		
		.andExpect(jsonPath("&[0].showId", Matchers.is(1)))
		.andExpect(jsonPath("&[0].title", Matchers.equalToIgnoringCase("Morning Vibes")))
		.andExpect(jsonPath("&[0].description", Matchers.containsStringIgnoringCase("refreshing morning show with great music and positive talks")))
		.andExpect(jsonPath("&[0].showTime", Matchers.equalToIgnoringCase("08:00 AM - 10:00 AM")))
		.andExpect(jsonPath("&[0].category", Matchers.equalToIgnoringCase("Music & Talk")))
		.andExpect(jsonPath("&[0].host", Matchers.equalToIgnoringCase("Emma Roberts")))
		.andExpect(jsonPath("&[0].duration", Matchers.is(120)))
		.andExpect(jsonPath("&[0].popularityRating", Matchers.is(5)))
		.andExpect(jsonPath("&[0].station_id", Matchers.is(1)))
		
		.andReturn();
	}
	
	
	

	
	
	
	
	
	
	private byte[] toJson(Object r) throws Exception{
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}
	
	
	private void print(String s) {
		System.out.println(s);
	}
	
	private void saveDataToFileSystem(Object key, Object value) throws Exception {
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
				if(!builder.toString().isEmpty()) {
					jsonObject = new JSONObject(builder.toString());
				}
			}catch (FileNotFoundException | JSONException e) {
				e.printStackTrace();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
			jsonObject.put((String) key, value);
			writer.write(jsonObject.toString());
			writer.close();
		}catch (JSONException | IOException e) {
			throw new Exception("Data not saved.");
		}
	}
	
	private Object getDataFromFileSyatem(String key) throws Exception{
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
		}catch(FileNotFoundException | JSONException e) {
			throw new Exception("Data not found. Check authentication and ID generations to make sure data is being produced.");
		}
	}

}
