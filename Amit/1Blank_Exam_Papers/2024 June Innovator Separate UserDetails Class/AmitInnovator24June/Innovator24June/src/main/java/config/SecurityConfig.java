package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import filter.JwtAuthFilter;

@Component
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Autowired
	UserInfoUserDetailsService userDetailsService;
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}
	
	AuthenticationProvider authenticationProvider() {
		//Stack pver flow error if not write dao authentication provider
		return null;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
	}
	
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return null;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationProvider config) {
		return null;
	}
	
}
