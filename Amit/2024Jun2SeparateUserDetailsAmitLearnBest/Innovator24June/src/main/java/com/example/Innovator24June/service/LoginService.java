package com.example.Innovator24June.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Innovator24June.entity.UserInfo;
import com.example.Innovator24June.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;



@Service
public class LoginService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	public ResponseEntity addUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		try {
			userInfo = userInfoRepository.save(userInfo);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userInfo);
			//this was SC_CREATED but i was giving OK 200 so test case fail.. 
			//test case fail has blue cross and test case error has red cross..
			//if test case pass then will not come in the screen
			//because of this all the test cases were failing..
			//because signup was failed..LoginService so all apis giving no data
			//but when running from postman, alla pis were successful.. because whiling signing up status was 200
			//so in test case no user inserted in the db as status expected was 201
			//but when manually doing... status did not matter.. on 200 also data saved in db table
			//so other apis worked fine
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Error in saving user in sign up api");
		}
	}
}
