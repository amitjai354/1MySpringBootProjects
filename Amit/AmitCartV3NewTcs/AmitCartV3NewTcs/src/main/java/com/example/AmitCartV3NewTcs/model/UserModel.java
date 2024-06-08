package com.example.AmitCartV3NewTcs.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.Table;

@Entity
@Table(name="Users")
public class UserModel implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer userId;
	
	String username;
	String password;
	
	@Column(unique = true)
	String email;
	
	//@JsonIgnore
	//@JsonIgnoreProperties({"username", "email", "isAccountNonExpired"})
	//this is jsona and json is in { "name":"Aj", "id":1 }

	@ElementCollection(targetClass = RoleModel.class, fetch=FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))//user_id, roles is created in user_role
	@Enumerated(EnumType.STRING)
	private Set<RoleModel> roles;
	
//	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
//				inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "roleId"))
//	private Set<RoleModel> roles;
	
//	@ManyToOne
//	@JoinColumn(name = "role_id", referencedColumnName = "roleId")
//	private RoleModel role;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		//return roles.stream().map(r->new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());//class RoleModel
		return roles.stream().map(r->new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());//enum RoleModel
		
//		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
//		return List.of(grantedAuthority);
//		return Collections.singletonList(grantedAuthority);
	}

	//Very very important
	//if implementing user details before writting getter setter, always username not found exception
	//as below code is returning null
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
	
	//working without this code as well, now does not override these below codes
	@Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();//works with this code also
        //return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
        //return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
        //return true;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
        //return true;
    }

	public UserModel() {
		super();
	}

	public UserModel(Integer userId, String username, String paswword, String email, Set<RoleModel> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = paswword;
		this.email = email;
		this.roles = roles;
	}

	public UserModel(String username, String paswword, String email) {
		super();
		this.username = username;
		this.password = paswword;
		this.email = email;
	}

	public UserModel(String username, String paswword, String email, Set<RoleModel> roles) {
		super();
		this.username = username;
		this.password = paswword;
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
		return password;
	}

	public void setPaswword(String paswword) {
		this.password = paswword;
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
