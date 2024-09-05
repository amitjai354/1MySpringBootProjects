package config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import filter.JwtAuthFilter;

public class SecurityConfig {
	
	private JwtAuthFilter authFilter;
	
	UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}
	
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("h2-console/**"));
	}
	
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		return null;
	}
	
	AuthenticationProvider authenticationProvider() {
		//Stack pver flow error if not write dao authentication provider
		return null;
	}
	
	AuthenticationManager authenticationManager(AuthenticationProvider config) {
		return null;
	}
	
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
