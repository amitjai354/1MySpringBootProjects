package com.example.demoTcsArtWorkNov23Innovator.security;

import com.example.demoTcsArtWorkNov23Innovator.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfiguration {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoder());
        provider.setUserDetailsService(loginService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c->c.disable())
                .authorizeHttpRequests(a->a.requestMatchers("/artWork/add").hasAuthority("Owner")
                        .requestMatchers("/artWork/list").hasAnyAuthority("Owner","Customer")
                        .requestMatchers("/artWork/update/**").hasAnyAuthority("Owner","Customer")
                        .requestMatchers("/artWork/delete/id/**").hasAnyAuthority("Owner", "Customer")
                        .anyRequest().permitAll())
                .exceptionHandling(e->e.authenticationEntryPoint(myAuthenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //if not writting this then h2 db is connecting but nothing loading.. we have to write frames disable
        //in above Security filter chain, but now evrything working with below code.. on spring official site
        //this web security configurer was given in tcs exam but that was for spring v2
        //below one is for v3
        //even if comment complete security filter chain, then also h2 db working completely, without password
        return (web) -> web.ignoring().requestMatchers("/h2-console/**")
                .requestMatchers("/login");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
