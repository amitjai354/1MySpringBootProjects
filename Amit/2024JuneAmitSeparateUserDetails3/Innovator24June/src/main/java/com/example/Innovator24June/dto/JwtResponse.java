package com.example.Innovator24June.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //bundles getter, setter, toString , @RequiredArgsConstructor, @EqualsAndHashcode annotations..
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
	private String accessToken;
	private int status;
	
	public JwtResponse() {
		super();
	}
	
	public JwtResponse(String accessToken, int status) {
		super();
		this.accessToken = accessToken;
		this.status = status;
	}

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
