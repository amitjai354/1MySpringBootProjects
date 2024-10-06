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
		.authorizeHttpRequests(a->a.anyRequest().permitAll())
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
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
