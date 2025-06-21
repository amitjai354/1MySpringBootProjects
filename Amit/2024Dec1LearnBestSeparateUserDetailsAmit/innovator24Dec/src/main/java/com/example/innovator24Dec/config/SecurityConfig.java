package com.example.innovator24Dec.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration //must write Configuration here not Component as Configuration has some other elemnts over Component
//usefull fotr configuring security.. if writing Component then getting error in test cases
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
	UserInfoUserDetailsService myUserDetailsService;
	
	@Autowired
	MyAuthenticationEntryPoint entryPoint;
	
	UserDetailsService userDetailsService() {
		return myUserDetailsService;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		//given in exam this only /h2-console
		return (web) -> web.ignoring().requestMatchers("/h2-console/**") //this causes to run smoothly otherwise h2-cosole ran but everthing blank, need to disbal framoptions
				.requestMatchers("/signUp/**") //this make these apis unauthenticated
				.requestMatchers("/login/**");
		//if not adding /signUp here, then giving unauthorized 401
		//not working with h.frameOption.disabled now in latest version
		
		//working without adding /login api as well because i had written h.frameOptions
		//as i am doing any request permit all	
		
		//i missed .anyRequest.permitAll so other apis were not running like signup and login.. better define in webcustomizer
		//as we had not writeen them in security filter chain so other apis were not running
		///h2-console was running because we had define here in web customizer
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//http.headers(h->h.frameOptions(f-> f.disable()));//h2 console site ran but nothing visible, everything blank 
		//as with csrf disble, only ran api but nothing visible
		//but with mentioning in web customizer, evrything visible
		
		///if do not write any post api like signup in webcustomizer and uncomment this.. not working
		//giving unauthorized 401 even when .anyRequest().permitAll()
		//working with this commented 
		//and uncommented both because above code i had written web customizer
		
		//try skipping authentication entry point here and check if test cases are passing
		//as this class was not given in exam
		
		
		//add these api urls from controller directly then in paper check what is authority
		//write one request matcher and copy pastee 4 times then just update url and authority save time
		
		http.csrf(c->c.disable()) //if do not write this, all post apis failing so all apis failing
		//.authorizeHttpRequests(a->a.requestMatchers(HttpMethod.GET, "/design/get/**").hasAnyAuthority("DESIGNER", "USER")
		.authorizeHttpRequests(a->a.requestMatchers("/design/get/**").hasAnyAuthority("DESIGNER", "USER")
				.requestMatchers("/design/add/**").hasAuthority("DESIGNER") 
				.requestMatchers("/design/update/**").hasAuthority("DESIGNER")
				.requestMatchers("/design/delete/**").hasAuthority("DESIGNER")
				//.requestMatchers("/signUp/**").permitAll() //even this is not working ..only works when define in
				//webcustomizer
				.anyRequest().permitAll())//if do not write this, public apis like getLust, getFilter are giving 401 unauthorized
			//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//if writting above line in the get/list api, giving 400 as get/list api is public so no authentication provided there
			//so this line gives some exception so bad request is returned
			//all public apis giving this error if write this line there
		.exceptionHandling(e->e.authenticationEntryPoint(entryPoint));
		//if comment this line, 2 test cases failing as getting 403 forbidden instead of 401 unauthorized
		//but in test case need , unauthorised
		
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
		
		
		//this worked in spring 3.3.4
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //this constructor is deprecated now
//		//provider.setUserDetailsService(myUserDetailsService);
//		provider.setUserDetailsService(this.userDetailsService()); //this is deprecated now
//		provider.setPasswordEncoder(this.passwordEncoder());
//		
		//provider.setUserDetailsPasswordService(null); //something new added
		
		//works in spring 3.5.0
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(myUserDetailsService); 
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
	}
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
}
