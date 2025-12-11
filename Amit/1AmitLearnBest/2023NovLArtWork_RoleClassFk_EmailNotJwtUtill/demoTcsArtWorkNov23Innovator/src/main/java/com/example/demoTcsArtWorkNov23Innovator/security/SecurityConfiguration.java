package com.example.demoTcsArtWorkNov23Innovator.security;

import com.example.demoTcsArtWorkNov23Innovator.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityConfiguration {

	@Autowired
    LoginService loginService;

	@Autowired
    AuthenticationFilter authenticationFilter;

	@Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/h2-console")
				.requestMatchers("/signUp")
				.requestMatchers("/login");
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.headers(h->h.frameOptions(f->f.disable()));
		
		http.csrf(c->c.disable())
		.authorizeHttpRequests(a->a.requestMatchers("/artWork/add/**").hasAuthority("OWNER")
				.requestMatchers("/artWork/add/**").hasAuthority("OWNER")
				.requestMatchers("/artWork/list/**").hasAnyAuthority("OWNER", "CUSTOMER")
				.requestMatchers("/artWork/update/**").hasAuthority("OWNER")
				.requestMatchers("/artWork/delete/**").hasAuthority("OWNER"))
		.exceptionHandling(e->e.authenticationEntryPoint(myAuthenticationEntryPoint));
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(loginService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return null;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configg) throws Exception {
		return configg.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
    
}
