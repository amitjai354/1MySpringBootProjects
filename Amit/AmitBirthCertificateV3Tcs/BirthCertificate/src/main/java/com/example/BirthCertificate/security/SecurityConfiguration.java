package com.example.BirthCertificate.security;

import com.example.BirthCertificate.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
public class SecurityConfiguration {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c->c.disable())
                .authorizeHttpRequests(a->a.requestMatchers("/certificate/add/**").hasAuthority("DOCTOR")
                        .requestMatchers("/certificate/list/**").hasAnyAuthority("MEDICALOFFICER", "DOCTOR")
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                        //.anyRequest().permitAll())
                //order of writting matters here
                //in authenticated: /addghhj api is giving you dont have permission 400 bad request instaed of Not Found 404
                //by rule all authenticated it should be otherwise anyone can write any api and fetch all
                //all apis should be properly authenticated except /login and /h2-console
                //in exam, you can make permit all, but actually authenticated it should be
                //permit all is perfect as if any wrong url then 404 Not Found but if make all autyenticated then
                //giving you dont have permisssion for wrong urls as well.. just make sure that all your api are
                // properly written here
                // problem is if our request matcher is wrong then also all our
                //api will work and if wrong token passed then also it will work as permit all so
                //authentication not required.. security context holder gives class cast exception
                //String can not be cast to User
                //if anyRequest.authenticated then once I restart app only first time this is working
                //after than saying you dont have permisiion authentication..
                //is this because filter is this oonce per request only
                //actually after once post was not working as already saved certificate..
                // unique certificate number so when second time error uniw key
                //but instead of error authenticated was saying you dont have permission
                .exceptionHandling(e->e.authenticationEntryPoint(myAuthenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers("/h2-console/**")
                .requestMatchers("/logon"));
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(loginService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }
}
