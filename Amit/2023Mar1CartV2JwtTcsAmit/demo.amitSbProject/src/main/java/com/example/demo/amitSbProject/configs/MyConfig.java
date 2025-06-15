package com.example.demo.amitSbProject.configs;

import com.example.demo.amitSbProject.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//given in tcs code
public class MyConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().ignoringAntMatchers("/h2/**");
       http.headers().frameOptions().disable();
       http.csrf().disable();
       //if do not write this.. gives forbidden for any post api weather api correct or not

       http.authorizeHttpRequests()
               //.antMatchers("/api/public/login").permitAll()
               //.anyRequest().authenticated();
               .antMatchers("/test1").authenticated()
               .anyRequest().permitAll()
               .and()
               .exceptionHandling().authenticationEntryPoint(apiAuthenticationEntryPoint);


       http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
                //.passwordEncoder(passwordEncoder());//need not write, still working same
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }

//    @Bean
//    public RegistrationBean jwtAuthFilterRegister(JwtAuthenticationFilter jwtAuthenticationFilter){
//        return new ServletRegistrationBean<>();
//    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
