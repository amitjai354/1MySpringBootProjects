package com.example.AmitCartV3NewTcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.AmitCartV3NewTcs.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//UserDetails userDetails = userRepo.findByEmail(email).orElseThrow(()-> new BadCredentialsException("Username is wrong"));
		UserDetails userDetails = userRepo.findByEmail(email).orElse(null);
		if(userDetails==null) {
			throw new BadCredentialsException("Username not found");
		}
		return userDetails;
	}

}
