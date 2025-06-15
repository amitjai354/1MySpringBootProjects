package com.example.AmitCartV3NewTcs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.AmitCartV3NewTcs.service.MyUserDetailsService;

@Configuration
public class ApiSecurityConfig {
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	MyAuthenticationEntryPoint myAuthenticationEntryPoint;
	
	@Autowired
	MyAuthenticationFilter myAuthenticationFilter;
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web->web.ignoring().requestMatchers("/h2-console/**")
				.requestMatchers("/login");
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//http.headers(h->h.frameOptions(f->f.disable()));
		http.csrf(c->c.disable())
		.authorizeHttpRequests(a->a.requestMatchers("/login").permitAll()
				//.requestMatchers("/product/search/**").permitAll() //can acces without token
				.requestMatchers("/product/search/**").hasAnyAuthority("CONSUMER", "SELLER") //access with token only, only consumer or seller can access
				.requestMatchers(HttpMethod.GET, "/consumer/cart/**").hasAuthority("CONSUMER")
				.requestMatchers("/consumer/cart/**").hasAuthority("CONSUMER")
				.requestMatchers(HttpMethod.GET, "/seller/product/**").hasAuthority("SELLER")
				.requestMatchers(HttpMethod.POST, "/seller/product/**").hasAuthority("SELLER")
				.requestMatchers("/seller/product/**").hasAuthority("SELLER")
				.anyRequest().permitAll())//with permitAll we can see 403 forbidden if consumer tries to access sellere api
				//and if no token passed then unAuthorised we will get that we gave in authentication entry point
				//but if make anyrequest authenticated then we will see everywhereonly what we gave in authentication entry point
		.exceptionHandling(e->e.authenticationEntryPoint(myAuthenticationEntryPoint));
		
		http.addFilterBefore(myAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserDetailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //in postman simply pass unencrypted password, only in db, saving encrypted password
		//return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
}
