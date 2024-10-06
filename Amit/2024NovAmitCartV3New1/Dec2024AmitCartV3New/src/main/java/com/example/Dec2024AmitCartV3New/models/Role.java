package com.example.Dec2024AmitCartV3New.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role {
	CONSUMER, 
	SELLER;
}

//public class Role {
//}

class RoleGrantedAuthority implements GrantedAuthority{
	
	private static final long serialVersionUID = 7147208211838127997L;
	
	String role;

	public RoleGrantedAuthority(String role) {
		this.role = role;
	}


	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.role;
	}
	
}