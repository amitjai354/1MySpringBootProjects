package com.example.innovator24Dec.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.innovator24Dec.repository.UserInfoRepository;

public class LoginService {

	private UserInfoRepository repository;
	
	PasswordEncoder passwordEncoder;
}
