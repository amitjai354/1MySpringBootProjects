package com.example.Dec2024AmitCartV3New.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Dec2024AmitCartV3New.service.UserAuthService;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig {
	//5 Beans : SecurityFilterChain, WebSecurity, AuthenticationManager, PasswordEncoder, AuthenticationProvider
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	private ApiAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private UserAuthService userAuthService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.headers(h->h.frameOptions(f->f.disable()));
		http.csrf(c->c.disable())
		.authorizeHttpRequests(a->a.requestMatchers(HttpMethod.GET, "/api/auth/consumer/cart/**").hasAuthority("CONSUMER")
				.requestMatchers("/api/auth/consumer/cart/**").hasAuthority("CONSUMER")//all cart api covered here
				.requestMatchers("/api/auth/seller/product/**").hasAuthority("SELLER")//this will cover all post, get, put delete apis
				.anyRequest().permitAll())
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
		//if trying to access public api login to generate jwt and password is wrong then also 401 unauthorized
		//as currently user is not logged in, password is wrong
		
		// if authenticated endpoints are accessed without JWT, return 401... 
		//here this authentication entry point works
		
		// if a consumer endpoint is accessed with seller JWT or viceverse, return 403... this we do Security
		//Configuration bean
		
		//if seller is accessing the seller api let say delete api but this seller is not the owner of this product
		//to be deleted then return Forbidden 403 or bad request 400.. this we check by getting logged in user
		//from Security context holder and then check if owner id is same as this logged inuser, 
		//if not return status 400 or 400 using Response entity
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web-> web.ignoring().requestMatchers("/h2-console/**")
				.requestMatchers("/login");
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userAuthService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}

}
