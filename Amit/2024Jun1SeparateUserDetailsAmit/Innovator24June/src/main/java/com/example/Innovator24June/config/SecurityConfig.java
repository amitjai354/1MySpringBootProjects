package com.example.Innovator24June.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;



@Configuration
public class SecurityConfig {
	
	@Autowired
	private com.example.Innovator24June.filter.JwtAuthFilter authFilter;
	
	@Autowired
	UserInfoUserDetailsService userDetailsService;
	
	@Autowired
	private MyAuthenticationEntryPoint entryPoint;
	
//	@Bean
//	UserDetailsService userDetailsService() {
//		return new UserInfoUserDetailsService();
//	}
	
//	@Bean
//	AuthenticationProvider authenticationProvider() {
//		//Stack pver flow error if not write dao authentication provider
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder(this.passwordEncoder());
//		return provider;
//	}
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		//Stack pver flow error if not write dao authentication provider
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
				.requestMatchers("/signUp/**").requestMatchers("/login/**");
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		http.csrf().ignoringAntMatchers("/h2/**");
//	    http.headers().frameOptions().disable();
//	    http.csrf().disable();
//	    if do not write this.. gives forbidden for any post api weather api correct or not
		//http.headers(h->h.frameOptions(f->f.disable()));
		http.csrf(c->c.disable())
		.authorizeHttpRequests(a->a.requestMatchers(HttpMethod.POST, "/ticket/add/**").hasAuthority("CLIENT")
				.requestMatchers("/ticket/list/**").hasAnyAuthority("CLIENT", "ADMIN")
				//i had written customer, seller so test case was giving 403 instead of 200
				.requestMatchers("/ticket/update/**").hasAuthority("ADMIN")
				.requestMatchers("/ticket/delete/**").hasAuthority("CLIENT")
				.anyRequest().permitAll())
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		//here i had written AuthenticationProvider so be careful
		return config.getAuthenticationManager();
	}
	
}
