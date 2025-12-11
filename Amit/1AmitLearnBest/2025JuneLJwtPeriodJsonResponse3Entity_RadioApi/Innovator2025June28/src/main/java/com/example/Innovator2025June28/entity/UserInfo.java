package com.example.Innovator2025June28.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserInfo {
	
	//in exam it jackartaPersistance.*;jakarta.persistence.Id,
	@Id //org.springframework.data.annotation.Id  did not run with this; this one gives very strange error
	//will never be able to solve as will not know that Id imported is wrong
	
	//Error creating bean with name 'jwtAuthFilter': Unsatisfied dependency expressed through field 'userDetailsService': 
	//Error creating bean with name 'userInfoUserDetailsService': Unsatisfied dependency expressed through field 'repository': 
	//Error creating bean with name 'userRepository' defined in com.example.Innovator2025June28.repository.UserRepository defined 
	//in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Cannot resolve reference 
	//to bean 'jpaSharedEM_entityManagerFactory' while setting bean property 'entityManager'
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@JsonProperty(value = "password", access = Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String roles;
	
	
	//test cases errors can see in the order of test cases execution, as all test cases executed in one go, so all error comes in the main log
	//so if error coming in the top test cases then check at top of the main log as sometimes test case separate log does not give full information
	//like assertion is failing but why assertion is failing this we will check in the main log not the test case log


	//if missed this default constrtuctor then user repo will not be able to save the user, it uses default constructor
	//even if all argument constructor is present, this error will come that default constructor is not present
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

	public UserInfo(String name, String email, String password, String roles) {
		super();
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
