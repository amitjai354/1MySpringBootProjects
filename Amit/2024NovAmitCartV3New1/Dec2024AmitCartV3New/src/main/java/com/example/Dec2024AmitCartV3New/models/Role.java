package com.example.Dec2024AmitCartV3New.models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public enum Role {
	CONSUMER, 
	SELLER;
}

//@Entity
//public class Role {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int roleId;
//	
//	private String roleName;
//
//	public Role() {
//		super();
//	}
//
//	public Role(int roleId, String roleName) {
//		super();
//		this.roleId = roleId;
//		this.roleName = roleName;
//	}
//
//	public int getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(int roleId) {
//		this.roleId = roleId;
//	}
//
//	public String getRoleName() {
//		return roleName;
//	}
//
//	public void setRoleName(String roleName) {
//		this.roleName = roleName;
//	}
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