package com.example.Dec2024AmitCartV3New.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
//	@JsonIgnore
//	@JsonIgnoreProperties("roleId", "roleName") 
//	when do not want to ignore complete class but just some attributes
	
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;
	
//	@ManyToMany(fetch=FetchType.EAGER, cascade= CascadeType.ALL)
//	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"),
//			inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "roleId"))
//	private Set<Role> roles;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="role_id", referencedColumnName = "roleId")
//	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(r->new RoleGrantedAuthority(r.name())).collect(Collectors.toList());
		//return roles.stream().map(r->new RoleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
		//r.getRoleName() if Role class instead of Role Enum
		
//		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());
//		return List.of(grantedAuthority);
	}

	//Below 2 fields must return getter otherwise always BadCredentials Exception
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
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
