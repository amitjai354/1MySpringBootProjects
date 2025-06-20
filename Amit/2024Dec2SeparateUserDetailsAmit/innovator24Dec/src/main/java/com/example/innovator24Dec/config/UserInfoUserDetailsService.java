package com.example.innovator24Dec.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.innovator24Dec.repository.UserInfoRepository;


//this class will be autowired in security configuration class and also we will autowire repo class here so use @Component

public class UserInfoUserDetailsService implements UserDetailsService{

	private UserInfoRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
