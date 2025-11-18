package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class CreateInMemoryUser_UserDetailService {
@Bean
public UserDetailsService userDetailsService(){
    UserDetails userDetails = User.builder()
            .username("amit")
            .password(passwordEncoder().encode("123"))
            .roles("ADMIN")
            .build();
    UserDetails userDetails1 = User.builder()
            .username("amit1")
            .password(passwordEncoder().encode("1234"))
            .roles("ADMIN")
            .build();
    return new InMemoryUserDetailsManager(userDetails, userDetails1);
}

@Bean
PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
}
