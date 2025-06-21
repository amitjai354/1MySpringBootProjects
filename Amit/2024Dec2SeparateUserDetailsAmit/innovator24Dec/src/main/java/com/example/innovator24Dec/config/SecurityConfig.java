package com.example.innovator24Dec.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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

import com.example.innovator24Dec.filter.JwtAuthFilter;

@Configuration
public class SecurityConfig {
	
	/*
	//check forbidden access //only designer can add not the user so forbidden in security config
	//this need to check that where we give forbidden
	mockMvc.perform(post("/design/add")
			.content(toJson(design))
			.header("Authorization", "Bearer "+getDataFromFileSystem(TOKEN_USER_1))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
	 */

	@Autowired
	private JwtAuthFilter authFilter;
	
	@Autowired
	UserInfoUserDetailsService userInfoUserDetailsService;
	
	@Autowired
	MyAuthenticationEntryPoint entryPoint;
	
	UserDetailsService userDetailsService() {
		return userInfoUserDetailsService;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/h2-console/**")
				.requestMatchers("/signUp/**")
				.requestMatchers("/login/**"); //given in exam this only
		//working without adding /login api as well because i had written h.frameOptions
		//as i am doing any request permit all	
		//i missed .anyRequest.permitAll so other apis were not running like signup and login.. better define in webcustomizer
		//as we had not writeen them in security filter chain so other apis were not running
		///h2-console was running because we had define here in web customizer
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//http.headers(h->h.frameOptions(f->f.disable()));//working with this commented 
		//and uncommented both because above code i had written web customizer
		
		//try skipping authenthication entry point here and check if test cases are passing
		//as this class was not given in exam
		
		http.headers(h->h.frameOptions(f->f.disable()));
		http.csrf(c->c.disable())
		.authorizeHttpRequests(a->a.requestMatchers("/design/add/**").hasAuthority("DESIGNER")
				//.requestMatchers("/design/list/**").hasAnyAuthority("DESIGNER")
				.requestMatchers("/design/get/**").hasAnyAuthority("DESIGNER", "USER")
				//.requestMatchers("/design/filter/**").hasAuthority("DESIGNER")
				.requestMatchers("/design/delete/**").hasAuthority("DESIGNER")
				.requestMatchers("/design/update/**").hasAuthority("DESIGNER")
				.anyRequest().permitAll())
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		//Stack over flow error if not write dao authentication provider
		//provider.setUserDetailsService(this.userDetailsService());//working with this code as well
		//provider.setUserDetailsService(myUserDetailsService);
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(this.userDetailsService());
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
}
