package com.example.Dec2024AmitCartV3New.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.ManyToOne;

@Entity
public class User implements UserDetails {
	
	private static final long serialVersionUID = -6817162083992336179L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name="user_role_new_table", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"),
//			inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "roleId"))
	private Set<Role> roles;
	
	
	//--------------------
	//if search ManyToOne with Enum in spring boot
	//2 cases, Role enum is not a table , just a enum class then do like below
	//we will not use ElementCollection or Collection table, 
	//@ManyToOne()//can not use with enum
	//@JoinColumn(name="role_id_fk", referencedColumnName = ) there is no id in enum
	
	@Enumerated(EnumType.STRING) //only rhis line is written
	Role role;  //user Table: username, password, role
	
	//each object of user will have its own role
	//while creating object of role, we can provide enum directly instead of providing role id
	
	//case 2: we will create a separate table for Role.. that is same as we do with ManyToOne
	//as here we will have roleId and roleName in the class.. we need to create class
	//new User("aj", "Aj123", "CONSUMER") 
	//Role role1= Role.CONSUMER;
	//new User("aj", "Aj123", role1) 
	//--------------------------------
	
	/*
	Choosing the right approach:
Direct enum mapping (@Enumerated):
Simpler to implement, suitable when enum values are static and don't require additional attributes 
or frequent changes.
Separate entity mapping (@ManyToOne):
Provides more flexibility, suitable when enum-like values are dynamic, have additional properties, 
or need to be managed as distinct entities in the database.
	 */
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//if ManyToOne with enum role
//		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());
//		return List.of(grantedAuthority); 
		
		return roles.stream().map(r->new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public User() {
		super();
	}

	public User(String username, String password, Set<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ "]";
	}

}
