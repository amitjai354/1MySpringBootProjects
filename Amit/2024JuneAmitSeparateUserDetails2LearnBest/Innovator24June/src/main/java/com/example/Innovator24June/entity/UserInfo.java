package com.example.Innovator24June.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data//all test cases passed with this annotation as well, but in project 
//some issue with this annotaion in test case run
//getter, setter others worked fine expect data annotation.. but here no issue working fine
//may be because i have added all the codes from source onlyy so no use of this annotation
//as soon as i commented below no arg constructor that i added using source.. started getting error
//must add all these source code by yourself.. do not use lombok.. otherwise unexpected errors

//@NoArgsConstructor //error is at this NoArgConstructor.. no error with Data
//must all source code.. both conbstructors and all getters and setters
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String email;
	private String password;
	private String roles;
	
	public UserInfo() {
		super();
	}
	//must add all these source code by yourself.. do not use lombok.. otherwise unexpected errors

	//No Arg Constructor is given by lombok annotation
	public UserInfo(int id, String name, String email, String password, String roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	

}
