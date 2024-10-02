package com.example.Innovator24June.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	UserInfoRepository userInfoRepository;

	public ResponseEntity addUser(UserInfo userInfo) {
		String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(encodedPassword);
		try {
			userInfo = userInfoRepository.save(userInfo);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("unable to save user");
		}
		return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userInfo);
	}
	
//	public UserInfo getUserByUsername(String username) {
//		return userInfoRepository.findByName(username).orElseThrow(()->new UsernameNotFoundException("invalid username"));
//		
////		Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
////		if(userInfo==null) {
////			throw new UsernameNotFoundException("invalid username");
////		}
////		return userInfo;
//		
//		//in load user by username .. in exam already autowired repository there so can directly write there..
//		//I had created thois method here..
//	}
}
