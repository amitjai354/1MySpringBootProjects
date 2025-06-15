package com.example.innovator24Dec.config;



import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.innovator24Dec.filter.JwtAuthFilter;

public class SecurityConfig {
	
	/*
	//check forbidden access //only designer can add not the user so forbidden in security config
	//this need to check that where we give forbidden
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_USER_1))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	 */

	private JwtAuthFilter authFilter;
	
	UserDetailsService userDetailsService() {
		return null;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/h2-console/**"); //given in exam
	}
	
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return null;
	}
	
	PasswordEncoder passwordEncoder() {
		return null;
	}
	
	AuthenticationProvider authenticationProvider() {
		return null;
	}
	
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return null;
	}
}
