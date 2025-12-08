package com.example.innovator24Dec.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


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
	
	/*
	GenerationType : IDENTITY, AUTO, SEQUENCE, TABLE, UUID added now
	IDENTITY: incremented by DB for each table
	SEQUENCE: maintains a sequence and updates id at overall all the tables
	TABLE: maintains a separate table for sequence
	UUID: creates uuid as pk
	AUTO: Based on db and type of pk, automatically selects either identity, or sequence or uuid
	*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)//can read get() but can not write set()
	//this is working perfectly as it ignores setter only not getter
	//if need getter then provide READ_ONLY
	//if need setter then provide WRITE_ONLY

	//@JsonIgnore
	//.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1))) this is failing if use 
	//@JsonIgnore as it will ignore both getter and setter, so can not find path error as it uses getter to get path
	private int id;
	//if keeping it as int then can not set id as null, which is must in new hibernate
	//but thing is primary id can not be null,, yeah pk will be updated by hibernate..
	//we just need null in json
	
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

	@JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)//can set only not get
	private String password;
	
	private String roles;
	
	
	/*
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="role_id", referencedColumnName = "roleId")
	@JsonIgnore
	@JsonIgnoreProperties({"roleId", "roleName"})
	RoleModel role;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="user_role_table", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
	@JsonIgnore //ignore roleSet in input and output whenever showing in result output
	Set<RoleModel> roleSet;
	
	
	@ElementCollection(fetch = FetchType.EAGER, targetClass = RoleModel.class)
	@CollectionTable(name="user_role", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"))
	@Enumerated(EnumType.STRING)
	Set<RoleModel> roleEnumSet;
	//here we do not have roleId as enum not class so no need to write inverseJoinColumns
	
	
	public enum RoleModel{
		DESIGNER, // "" is not used here
		USER
	}
	
	
	//---------- authorities() ------------
	for @ManyToMany
	Set<RoleModel> roles;
	
	return roles.stream().map(r-> new SimpleGrantedAuthority(r.name)).collect(Collectors.toList());//enum 
	//enum does not have roleName, so use r.name()
	return roles.stream().map(r-> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());//class
	
	
	for @ManyToOne()
	RoleModel role;
	
	Grantedauthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
	Grantedauthority grantedAuthority = new SimpleGrantedAuthority(role.name());// we can directly get roleName by
	//role.name as object can directly access attributes instead of role.getName()
	return List.of(grantedAuthority);
	*/
	

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
