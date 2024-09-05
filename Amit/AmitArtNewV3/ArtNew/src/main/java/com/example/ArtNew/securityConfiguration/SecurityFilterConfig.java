package com.example.ArtNew.securityConfiguration;

import com.example.ArtNew.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//32 min
@Configuration
public class SecurityFilterConfig {

    //net 2 hr in testing all and evry api.. with some errors also

    ///mistakes I did.. did not jax b api.. mandatory otherwise error but not given in exam so be carefull
    //may be needed now in version 3, but not needed in version 2
    //
    //I missed to write application properties file.. directly started jwt utill..
    // but first I write h2, jpa properties

    //need to write first user model the user repository then user service then login service load by username
    //and then RoleModel and then data loader and then check all annotations written or not..
    // or working in whci class, same time write all annotations
    //then write application properties h2, jpa
    //then jwt utill then authenticationFilter, then entry point then configs
    //then service , controller codes

    //we have to write ArtModel code also.. just check if fk created and unique and everything written

    //last time i missed depemdency h2 so check dependency then properties then jwt utill config then controllers

    //it tok total 1hr 17 min to write and run and test till token and h2 db

    //must cross check request matchers are written properly after controllers are written

    //with postman msg, Bad request means not passing request param or bosy
    //400 not found means url is wrongf
    //method not allowed means get post is wrong
    //once check all api without token so that you can check if request matcher are properly written
    //you will get you msg from entry pouint when permit all

    //also check by passing token of customer, you should get 403 forbidden by postman msg if request matcher is
    //properly written

    //Be very carefull methods should be public
    //'getArtByMedium(java.lang.String)' is not public in 'com.example.ArtNew.service.ArtService'.
    //Cannot be accessed from outside package, this was default

    @Autowired
    MyAuthenticationFilter filter;

    @Autowired
    MyAuthenticationEntryPoint entryPoint;

    @Autowired
    LoginService loginService;

    @Bean
    UserDetailsService userDetailsService(){
        //no error because of this.. still working
        //can comment this or can leave as it is.. no problem
        return loginService;
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        //this was given in exam.. Authentication Provider is parent so can return any type of provider in this
        //we need Database authentication here so we will create object of DaoAuthentication provider and return

        //AuthenticationProvider authenticationProvider = new AuthenticationProvider() {
        //this will not work, need to implement some methods here if create this object
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(loginService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(loginService);
//        provider.setPasswordEncoder(this.passwordEncoder());
//        return provider;
//    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers("/h2-console/**")
                .requestMatchers("/login");
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http.headers(h->h.frameOptions(f->f.disable()));
        http.csrf(c->c.disable())
                .authorizeHttpRequests(a->a.requestMatchers("/artWork/add/**").hasAuthority("OWNER")
                        .requestMatchers("/artWork/list/**").hasAnyAuthority("OWNER", "CUSTOMER")
                        .requestMatchers("/artWork/update/**").hasAuthority("OWNER")
                        .requestMatchers("/artWork/delete/**").hasAuthority("OWNER")
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().permitAll())
                .exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }
}
