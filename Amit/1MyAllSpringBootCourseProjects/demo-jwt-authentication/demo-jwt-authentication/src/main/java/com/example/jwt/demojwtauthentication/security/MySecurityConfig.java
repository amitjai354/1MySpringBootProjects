package com.example.jwt.demojwtauthentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
//@EnableMethodSecurity //to use @Preauthorize .. assign authority at class level
//@EnableWebSecurity
//public class MySecurityConfig extends WebSecurityConfigurerAdapter { //spring less than 3
public class MySecurityConfig{//spring 3

    @Autowired
    MyUserService myUserService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //***************** __spring less than 3 __***************************
/**  code for spring 2.7.12.. change security version to same in pom
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserService);
//    }

    // IMP NOTE: Always define your matches from most restricted to least restrcited manner
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
////                .antMatchers("/devops/**").hasAuthority(DEVOPS_AUTHORITY)
////                .antMatchers(HttpMethod.POST, "/developer/**").hasAuthority(DEVOPS_AUTHORITY)
////                .antMatchers("/developer/**").hasAuthority(DEVELOPER_AUTHORITY)
////                .antMatchers("/**").permitAll()
//
//
//               .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/normal/**").hasAuthority("NORMAL")
//            .antMatchers("/public/welcome")
//            .permitAll()
//            .antMatchers("/myuser")
//               .permitAll()
////               .anyRequest()
////                .authenticated()
////                .and() //For removal in 7.0. Use the lambda based configuration instead. may be inside authorizehttprequest
////                .formLogin();//all these requires customizers now
//                .and()
//                .formLogin();
//    }

    // CSRF - Cross site request forgery
**/

//************************************* --spring 3 __*********************
//below ois code fopr spring 3.. user detail service and in memeory
//this is for all dao authentication
@Bean
public DaoAuthenticationProvider daoAuthenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(myUserService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}

    //this is for inmemory authentication
//    @Bean // to configure user// creating users in memory.. for db can create user, userService, UserRepository class
//          //then call userService to create user and pass user
//    public UserDetailsService userDetailsService() {
//        UserDetails myNormalUser = User.withUsername("aj")
//                .password(passwordEncoder().encode("aj123"))
//                .roles("NORMAL")
//                .build();
//        UserDetails myAdminUser = User.withUsername("amit")
//                .password(passwordEncoder().encode("amit123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(myNormalUser, myAdminUser);//this is implementation class of UserDetailsService
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //instead of overriding now creating bean.. rest all same inside part
        httpSecurity.csrf().disable()
                //.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())//we need to passs customizer now in all
                .authorizeHttpRequests()
//                .requestMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .requestMatchers("/normal/**")
//                .hasRole("NORMAL")
                .requestMatchers("/public/welcome")
                .permitAll()
                .requestMatchers("/myuser")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and() //For removal in 7.0. Use the lambda based configuration instead. may be inside authorizehttprequest
                .formLogin();//all these requires customizers now

        return httpSecurity.build();
    }


    //    @Override
//    protected void Configure(HttpSecurity http) throws Exception(){
//        //old way of doing.. http.
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        //instead of overriding now creating bean.. rest all same inside part
//        httpSecurity.csrf().disable()
//                //.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())//we need to passs customizer now in all
//                .authorizeHttpRequests((authz)->authz
//                .requestMatchers("/public/welcome")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                )//instead of and use this close bracket
//                //.and() //For removal in 7.0. Use the lambda based configuration instead. may be inside authorizehttprequest
//                .formLogin();//all these requires customizers now
//
//        return httpSecurity.build();
//    }


}


///**************************************************************
//**************************************************************

//Spring Boot 3.0 | Configuring Spring Security in Spring Boot 3.0 | New way to configure Security
//even csrf, authorize http securty all are deprecated

/**
 * WebSecurityCongurerAdapter is deprecated now.. need to WebSecurtiyConfiguration
 * <dependency> this is also noty working now
 * <groupId>org.springframework.security</groupId>
 * <artifactId>spring-security-config</artifactId>
 * </dependency>
 *
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-parent</artifactId>
 * <version>3.1.0</version>//after 2.7.2 now this WebSecurityConfigurerAdapter is removed.. mid 2022
 * Durgesh has created video.. sesrch webSecurityConfigurerAdapter is de[rcated
 * spring suggesrt insetead if extend and override.. now use component based.. just create bean
 * rest they will inject where neede
 * <p>
 * //How to write userEDetailService authentication(for all db not just relational) in new way..
 * search,, web security configurer adapter is deprecated
 * *****************
 * I have managed to update the methods. This is the WebSecurityConfig class, and the methods are modified in the following way:
 * <p>
 * public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
 * authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
 * }
 * has become
 *
 * @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
 * return authenticationConfiguration.getAuthenticationManager();
 * }
 * In the old version you inject AuthenticationManagerBuilder, set userDetailsService, passwordEncoder and build it. But authenticationManager is already created in this step. It is created the way we wanted (with userDetailsService and the passwordEncoder).
 * <p>
 * Next, the configure() method for HttpSecurity is replaced by filterChain method as it is explained on the official site:
 * <p>
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter.
 * import com.myproject.UrlMapping;
 * import lombok.RequiredArgsConstructor;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.security.authentication.AuthenticationManager;
 * import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
 * import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 * import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
 * import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 * import org.springframework.security.config.http.SessionCreationPolicy;
 * import org.springframework.security.core.userdetails.UserDetailsService;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import org.springframework.security.crypto.password.PasswordEncoder;
 * import org.springframework.security.web.SecurityFilterChain;
 * import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 * import org.springframework.web.servlet.config.annotation.CorsRegistry;
 * import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 * @Configuration
 * @EnableWebSecurity
 * @EnableGlobalMethodSecurity(prePostEnabled = true)
 * @RequiredArgsConstructor public class SecurityConfig {
 * private final UserDetailsService userDetailsService;
 * <p>
 * private final AuthEntryPointJwt unauthorizedHandler;
 * <p>
 * private final AuthTokenFilter authenticationJwtTokenFilter;
 * @Bean public PasswordEncoder passwordEncoder() {
 * return new BCryptPasswordEncoder();
 * }
 * @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
 * return authenticationConfiguration.getAuthenticationManager();
 * }
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 * http.cors().and().csrf().disable()
 * .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
 * .authorizeRequests()
 * .antMatchers(UrlMapping.AUTH + UrlMapping.SIGN_UP).permitAll()
 * .antMatchers(UrlMapping.AUTH + UrlMapping.LOGIN).permitAll()
 * .antMatchers(UrlMapping.VALIDATE_JWT).permitAll()
 * .antMatchers("/api/test/**").permitAll()
 * .anyRequest().authenticated();
 * <p>
 * http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
 * <p>
 * return http.build();
 * }
 * @Bean public WebMvcConfigurer corsConfigurer() {
 * return new WebMvcConfigurer() {
 * @Override public void addCorsMappings(CorsRegistry registry) {
 * registry.addMapping("/**")
 * .allowedMethods("*");
 * }
 * };
 * }
 * }
 */