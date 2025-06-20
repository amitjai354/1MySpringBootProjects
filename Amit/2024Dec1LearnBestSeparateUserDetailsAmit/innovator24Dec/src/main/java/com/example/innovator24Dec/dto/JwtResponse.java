package com.example.innovator24Dec.dto;

//lombok not given this time in exam only @Entity given dec 2024
//@Data //bundles getter, setter, toString , @RequiredArgsConstructor, @EqualsAndHashcode annotations..
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//all test cases passed with these codes uncommented because i have added required source code in the code below
//otherwise failing test cases
//if uncomment above codes.. and comment codes below.. error test cases failing.. lombok not working
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
