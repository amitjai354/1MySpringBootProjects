package com.example.AmitCartV3NewTcs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public enum RoleModel{
	CONSUMER,
	SELLER;
}

//@Entity
//public class RoleModel {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	Integer roleId;
//	
//	@Column(unique = true)
//	String roleName;
//
//	public RoleModel() {
//		super();
//	}
//
//	public RoleModel(Integer roleId, String roleName) {
//		super();
//		this.roleId = roleId;
//		this.roleName = roleName;
//	}
//
//	public RoleModel(String roleName) {
//		super();
//		this.roleName = roleName;
//	}
//
//	public Integer getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(Integer roleId) {
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
//	
//}
