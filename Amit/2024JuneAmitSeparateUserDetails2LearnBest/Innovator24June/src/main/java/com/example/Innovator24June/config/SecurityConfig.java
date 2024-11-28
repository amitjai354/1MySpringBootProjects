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

import com.example.Innovator24June.filter.JwtAuthFilter;



@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Autowired
	UserInfoUserDetailsService userDetailsService1;//done by me
	
	@Autowired
	private MyAuthenticationEntryPoint entryPoint;
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		//Stack pver flow error if not write dao authentication provider
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userDetailsService());//working with this code as well
		//provider.setUserDetailsService(userDetailsService1);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
				//.requestMatchers("/signUp/**").requestMatchers("/login/**");//working with code commented as well
		//as i am doing anyrequest permit all
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.headers(h->h.frameOptions(f->f.disable()));//working with this commented and uncommented both
		http.csrf(c->c.disable())
		.authorizeHttpRequests(a->a.requestMatchers(HttpMethod.POST, "/ticket/add/**").hasAuthority("CLIENT")
				.requestMatchers("/ticket/list/**").hasAnyAuthority("CLIENT", "ADMIN")
				.requestMatchers("/ticket/update/**").hasAuthority("ADMIN")
				.requestMatchers("/ticket/delete/**").hasAuthority("CLIENT")
				.anyRequest().permitAll())
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		//check failed ticket testcase failing if do not write this authentication entry point..
		//but this whole class was not given in the exam.. i added it by myself
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
		return config.getAuthenticationManager();
	}
	
}
