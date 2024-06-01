package com.example.AmitCartV3NewTcs.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class UserModel implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer userId;
	
	String username;
	String paswword;
	
	@Column(unique = true)
	String email;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
				inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "roleId"))
	private Set<RoleModel> roles;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserModel() {
		super();
	}

	public UserModel(Integer userId, String username, String paswword, String email, Set<RoleModel> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.paswword = paswword;
		this.email = email;
		this.roles = roles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPaswword() {
		return paswword;
	}

	public void setPaswword(String paswword) {
		this.paswword = paswword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
