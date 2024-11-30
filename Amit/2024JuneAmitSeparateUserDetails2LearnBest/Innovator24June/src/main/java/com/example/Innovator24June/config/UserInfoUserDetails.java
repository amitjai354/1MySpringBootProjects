package com.example.Innovator24June.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Innovator24June.entity.UserInfo;


//@Configuration  if writing this here.. giving error.. as it need userInfo of bean type now 
//error could not find bean userInfo..
public class UserInfoUserDetails implements UserDetails{
	
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoUserDetails(UserInfo userInfo) {
		this.name = userInfo.getName();
		this.password = userInfo.getPassword();
		String[] rolesArray = userInfo.getRoles().split(", ");
		this.authorities = Stream.of(rolesArray).map(r-> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
	}
	
	//to avoid mistakes..
	//best is when writting any class.. please stay 1 more minute.. but check all annotations..
	//all lines all codes once again.. then move to other class
	//please very carefully check..
	
	//if any mistake, error.. first check all the annotations are there or not..
	//it may also happen that you have written annotation where not required as well
	
	//error if even after checking above code..
	//now check all the code.. line by line.. have you written all code in correct block.. or all blocksclosing orpoerly..
	//written filter chain ijside if block but it should have been outsside if block
	
	//mistakes i did..
	////i was saving ticketModel instead of ticketModelFromDb in update api so test case was failing that assertion failed
	//priority and status were not matching..
	
	//filter chain i had written inside .. niot outside all if blocks.. so without filterchain , all the apis were giving 200
	//even the wrong url ones were giving 200 instead of not found
	
	//i missed .anyRequest.permitAll so other apis were not running like signup and login.. better define in webcustomizer
	
	//did not write restcontroller annoation on login controller
	//wrote configuration annotation on this class but this class is not bean so not needed
	//i9n signup returned 201 instead of 200 so no user added in db so all the yest cases returning no data found
	//all the test cases failed becuse of this
	//in jwtfilyter class return type is respoinse entity so expected 400 but i was throwing error
	//throw new BadCredentialError().. thuis was wrong
	//formater.format(myDate) returns formated date nut myDate will always be same.. this will not change after format
	//lombok annotations are not working so add your code from source.. getter setter and 2 comnstructors
	////if do not write 1000, token is expiring immediately
	//STandardCharset.UTF_8 supports $ as well in the string secret but Base64 does not support
	
	//in test case red cross means error in the test case.. could not execute test case completely
	//blue croos mean test case failed after completely executing..
	//if pass then will not show in test caese screen log
	//if test case 1 failed.. eg test case for signup failed.. then no data user will be added in db so 
	//remaing other testcases will fail with error no data found.. so test case should pass from top..
	//so first solve issue in top test cases.. remaining will automatically cirrect
	//as soon as i fixed first signup, all 13 test cases passed inine go

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
