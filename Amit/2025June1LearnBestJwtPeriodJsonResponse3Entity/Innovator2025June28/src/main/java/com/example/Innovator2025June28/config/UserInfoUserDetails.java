package com.example.Innovator2025June28.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Innovator2025June28.entity.UserInfo;

public class UserInfoUserDetails implements UserDetails {
	
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoUserDetails(UserInfo userInfo) {
		this.name = userInfo.getName();
		this.password = userInfo.getPassword();
		
		//this will also work as role is single only, this is easier to do in exam no confusion in anxiety
//		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userInfo.getRoles());
//		this.authorities = List.of(grantedAuthority);
		
		String[] rolesArray = userInfo.getRoles().split(", ");
		this.authorities = Stream.of(rolesArray).map(r-> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
		
		//Stream.of(rolesArray).map(r-> new SimpleGrantedAuthority(r)).collect(Collectors.toList());

		//yaha par mai this.authorities = lagana bhool gya.. to all authenticated apis giving 400
		//debug se kuch nhi pata chla kyunki wo api tak aa hi nhi rha tha seedhe 403 de diya unchecked exception
		//na ho dubug me kuch de rha tha
		//maine khud se socha ki 403 kyu de rha.. means role nhi mil rha usko..
		//Security filter chain me dekha sab sahi tha.. phir bhi 403 hi tha.
		//authority ham yaha bhi assign krte hain so i checked here, and found the mistake
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}
	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonExpired();
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonLocked();
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}



	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserDetails.super.isEnabled();
	}


}
