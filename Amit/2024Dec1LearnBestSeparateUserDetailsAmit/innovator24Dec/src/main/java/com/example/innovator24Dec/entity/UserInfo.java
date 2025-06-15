package com.example.innovator24Dec.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


//lombok not given this time in exam only @Entity given dec 2024
//@Entity
//@Data//all test cases passed with this annotation as well, but in project 
//some issue with this annotation in test case run
//getter, setter others worked fine expect data annotation.. but here no issue working fine
//may be because i have added all the codes from source only so no use of this annotation
//as soon as i commented below no arg constructor that i added using source.. started getting error
//must add all these source code by yourself.. do not use lombok.. otherwise unexpected errors

//@NoArgsConstructor //error is at this NoArgConstructor.. no error with Data
//must all source code.. both constructors and all getters and setters

@Entity
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String name;
	
	@Column(unique = true)
	private String email;
	
	
	//Using @JsonIgnore or @JsonProperty to hide sensitive data in JSON response
	//if write @JsonIgnore here it will be ignored in request also.. can not take password in request
	//one way is to user this at getter only but not setter so that we can set password but can not get
	//password using getter..  
	//@JsonIgnore
    //public String getPassword() {return password;}
	//Since version 2.6: a more intuitive way is to use the com.fasterxml.jackson.annotation.JsonProperty 
	//annotation on the field. You can use appropriate access value as per your use case.
    //@JsonProperty(access = Access.WRITE_ONLY)
    //private String password;

	@JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String roles;

	public UserInfo() {
		super();
	}

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

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", roles="
				+ roles + "]";
	}
	
}
