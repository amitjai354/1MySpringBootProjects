package com.example.Dec2024AmitCartV3New.dto;

public class JwtResponse {
	String token;
	int status;
	public JwtResponse(String token, int status) {
		super();
		this.token = token;
		this.status = status;
	}
	public JwtResponse() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
