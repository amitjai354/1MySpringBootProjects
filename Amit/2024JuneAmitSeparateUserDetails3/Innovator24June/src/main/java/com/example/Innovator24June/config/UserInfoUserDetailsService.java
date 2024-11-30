package com.example.Innovator24June.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Innovator24June.entity.UserInfo;
import com.example.Innovator24June.repository.UserInfoRepository;


@Service
public class UserInfoUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserInfoRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo userInfo = repository.findByName(username).orElseThrow(()->new UsernameNotFoundException(username));
		return new UserInfoUserDetails(userInfo);
	}

}
