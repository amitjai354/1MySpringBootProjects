package com.example.Innovator24June.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Innovator24June.entity.UserInfo;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


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
	
	//=================================
	//steps to avoid mistakes..
	
	//best is when writting any class.. please stay 1 more minute.. but check all annotations..
	//all lines all codes once again.. then move to other class
	//please very carefully check..
	
	//if any mistake, error.. first check all the annotations are there or not..
	//it may also happen that you have written annotation where not required as well
	
	//error if even after checking above code..
	//now check all the code.. line by line.. have you written all code in correct block.. or all blocksclosing orpoerly..
	//written filter chain ijside if block but it should have been outsside if block
	
	//=============================================
	//exams mistake
	
	//.signWith(getSignKey(), SignatureAlgorithm.HS256)//all test cases passing with this
	//.signWith(SignatureAlgorithm.HS256, getSignKey())//deprecated
	//this was given in this exam..  i missed only this line.. was getting unable to parse Jwt token
	//error in sign with.. bas ye kar deta ho jata..
	
	//they had not given signUp in controller, so 404 not found..  means this api url does not exist..
	//so either Restcontroller not added or RestController added but did not create api itself
	//had to create by mysel in LoginControler,, but was given addUser() in LoginService
	
//	private SecretKey getSignKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);//this one was given in the exam.. 
//		//String should be Base64 decode
//		//byte[] keyBytes = this.JWT_SECRET.getBytes(StandardCharsets.UTF_8);//this one supports $ as well in SECRET
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
	
	//mera sara ho gya tha.. 2 hr 9 min mile the..
	//ek api hard thi wo chor diya tha maine.. uske chakkr me kuch na hota..
	//data loader bhi likh diya tha iss bar  .. yhi ek extra tha bas.. 15 min alag se lge..
	//baki get ki 3 api thi,, maine 2 kari thi.. hard wali chhor di thi..
	//signUp nhi tha LoginController me.. wo bhi kar diya
	//bas wahi signWith in jwt service me erro aa gya sara..
	//, SignatureAlgorthm.HS256 likhna tha.. starting me yaad tha.. last tak bhool gya.. actually 1 min tha bas..
	//5 se 10 min hota to pakka soch kar kar leta.. last 1 min me mind kaam hi nbhi krta..
	//only changed Key to SecretKey but same issue..
	
	//main is that first create and test add ticket api only.. ek api authenticate ho gyi to baki sari ho jayengi..
	//delte update get ki 2 api ki jagah add wali test krta to pakk ho jata issue resolve.. phir sara kr leta..
	//koi matlab nhihai jab add ticket ho nhi chali to... so first test add wali api.. 
	//if that add runs then write other apis code
	
	//same add user me bhi signUp nhi chalegi to baki kuch nhi chalega.. so first test sign up and login..
	//bina user ke koi api nhi chalegi..
	
	
	//=========================
	//mistakes i did..
	
	
	//@Configuration > @Component .. if using @Bean in a class, must be @Configuration not @Component, otherwise error that autowiring repository is giving null at DAoAuthenticationProvider
	
	//missed wrtting signWith in Jwts.. error that Jwts parse error..
	
	//missed returning ResponseEntity.. so code executed till last line.. different status got..
	
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
