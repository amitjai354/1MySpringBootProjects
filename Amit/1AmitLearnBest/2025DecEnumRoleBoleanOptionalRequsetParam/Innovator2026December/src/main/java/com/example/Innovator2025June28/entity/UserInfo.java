package com.example.Innovator2025June28.entity;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class UserInfo {
	
	//Written as per new Paper with enum user roles
	
	@Id //org.springframework.data.annotation.Id; or jakarta.persistence.Id, one gives very strange error
	//will never be able to solve as will not know that Id imported is wrong
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
//	@Column(unique = true, nullable = false)
//	private String email;
	
	@JsonProperty(value = "password", access = Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;
	
//	@Column(nullable = false)
//	private String roles;
	
	@Enumerated(EnumType.STRING)
	private UserRoles userRoles;

	
	public UserInfo() {
		super();
	}


	public UserInfo(int id, String name,  String password, UserRoles userRoles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.userRoles = userRoles;
	}
	
	public UserInfo( String name,  String password, UserRoles userRoles) {
		super();
		
		this.name = name;
		this.password = password;
		this.userRoles = userRoles;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public UserRoles getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(UserRoles userRoles) {
		this.userRoles = userRoles;
	}

	
	
	
}
