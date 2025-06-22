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
	ApiAuthenticationEntryPoint entryPoint;
	
	@Autowired
	JwtAuthenticationFilter filter;
	
	@Autowired
	UserAuthService myUserDeatailsService;
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web->web.ignoring().requestMatchers("/h2-console/**")
				.requestMatchers("/signUp/**")
				.requestMatchers("/login/**");
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.headers(h->h.frameOptions(f->f.disable()));
		
		http.authorizeHttpRequests(a->a.requestMatchers("/api/auth/consumer/cart/**").hasAuthority("CONSUMER")
				.requestMatchers("/api/auth/seller/product/**").hasAuthority("SELLER")
				.anyRequest().permitAll())
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configg) throws Exception {
		return configg.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserDeatailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
}
