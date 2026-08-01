package com.example.Innovator2025DecemberMostImp.entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
	
////===================================================================================================================
	//--------------------------------------
	//here nothing to write like mapped by in role class as it enum not a class
	@Enumerated(EnumType.STRING)
	private UserRoles userRoles;
	
//	SimpleGrantedAuthority authorityEnum = new SimpleGrantedAuthority(userRoles.name());
//	List<SimpleGrantedAuthority> listAuthEnum1 = List.of(authorityEnum);
//	
//	//--------------------------------------------
//	//where we write joincolumn, fk is created in that class
//	//in other classs we write mappedBy to tell that to which fk that table is joined
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="user_id", referencedColumnName = "userId") 
//	private UserRoles userRolesClass;
//
//	@OneToMany(mappedBy = "id")
//	private UserInfo userInfoRoleClass;
//	
//	//if role is a classs then it will be------------------------userRolesClass.getRoleName()
//	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRolesClass.name());
//	List<SimpleGrantedAuthority> listAuth = List.of(authority);
//	
//	//--------------------------------
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"),
//			inverseJoinColumns = @JoinColumn(name ="role_id", referencedColumnName = "roleId"))
//	private List<UserRoles> userRolesClassList;
//	
//	//normally it will r.getRole as userRole will be a class with getter-------------------------------r.getRoleName()
//	List<SimpleGrantedAuthority> listAuth1 = userRolesClassList.stream().map(r->new SimpleGrantedAuthority(r.name()))
//															.collect(Collectors.toList());
//	@ManyToMany(mappedBy = "")
//	@JsonIgnoreProperties({"id", "name"})
//	private List<UserInfo> userInfoListInRole;
//	
//	//-------------------------------------------------
//	//here nothing to write like mapped by in role class as it enum not a class
//	@ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
//	@CollectionTable(name="user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"))
//	private List<UserRoles> userRolesListEnum;
//	
//	List<SimpleGrantedAuthority> listAuthEnum = userRolesClassList.stream().map(r->new SimpleGrantedAuthority(r.name()))
//			.collect(Collectors.toList());
//	
//	//-----------------------------------------
//	//-------UserDetails class-----------
//	List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRoles.name()));
//	
//	String[] roleStringArray = name.split(", ");
//	List<GrantedAuthority> listAuthUserDetails = Stream.of(roleStringArray).map(r->new SimpleGrantedAuthority(r))
//						.collect(Collectors.toList());
////=========================================================================================================================
	
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
