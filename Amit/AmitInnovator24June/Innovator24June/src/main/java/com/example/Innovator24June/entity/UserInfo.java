package com.example.Innovator24June.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data //this is causing error in running test cases..
/*I was having this issue using lombok's @Data and @Builder annotations alone, which I replaced with:
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder*/
//after commenting @Data.. junit test started working
//be careful with lombok.. do not use any annotation of it.. add sources for constructor.. getter setter
//@NoArgsConstructor
//public class UserInfo implements UserDetails {
public class UserInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3309768664817880543L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name; //not mentioned in exam to make this unique
	private String email;
	private String password;
	private String roles;
	
	//No Arg Constructor is given by lombok annotation
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
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roles);
//		return List.of(grantedAuthority);
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return this.name;
//	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
//	@Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
