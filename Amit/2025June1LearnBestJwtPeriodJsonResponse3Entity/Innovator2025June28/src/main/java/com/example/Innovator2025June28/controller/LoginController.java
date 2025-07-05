package com.example.Innovator2025June28.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator2025June28.dto.AuthRequest;
import com.example.Innovator2025June28.dto.JwtResponse;
import com.example.Innovator2025June28.entity.UserInfo;
import com.example.Innovator2025June28.service.JwtService;
import com.example.Innovator2025June28.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
	
//in security config in exam , I have written these
//Red api by "ADMIN", Green by "USER", Blue by anyone without requiring authentication, JWT Token
//Blue api: /signUp, /login, /station/list, /show/list
//Green: /show/get/airing, /show/popularShow
//Red: /station/add, /station/update, show/add

//Status code: check in test case for each api
// /signUp : 201 and nothing given in exam
// /login : 200, 400 Invalid Crdentials!, 
// /station/list : 200, in test case unauthrized and Forbidden but that handled from security config
// /show/list : 200 and nothing given in exam, in test case unauthrized and Forbidden but that handled from security config
// /show/get/airing : 200 400 if no data, 
// /show/popularShow : 200 and nothing given, in test case unauthrized and Forbidden but that handled from security config
// /station/add : those who have role ADMIN and operator id point to the owner who created station details 201
// /station/update : authenticated user have role ADMIN and is creator of id object, 200, Forbidden if not the creator, 400 if no data found
// /show/add : 201 400 is any validation issue like missing required fields or invalid station id
	
	@Autowired
	private LoginService service;
	 
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	 
	

	//Very Very  Important when run code after security config only for first time
	//My errors in june 2025 in anxiety when first time half code only in exam:
	
	//all test cases failed, login test cases chale nhi and i was confused that ki dataloader se data dala hai to
	//get apis chalni chahiye at leat jo public hain.. but na to maine koi code likha tha for findAll na hi anotation likhe the
	//on controller and service
	//after security codes , focus on login and signup api only and nothing else will run
	//as abhi na to koi annotation likha hai on controller and service and na hi findAll code likha hai
	
	//yaha to security code likhte hi 3 test cases success: failed login, success login adminb and user
	//signup failed due to email nulls first, unique key violation as same email given in test case that was loaded in data loader
	//but waha ye 3 bhi nhi chale.. due to some jwt token issue, login me token nhi generate hua sahi se
	//then other apis me token pass nhi hua sahi se
	
	//when we run first after all security configuration codes: Response status expected:<200> but was:<404>
	//404 not found because we have not written @RestController above this class yet
	//but if we write above this class only and not in service class, very random error in test cases
	//when you will normally run then you will get the error that bean not created or something
	
	//after just writing RestController and all anotation in controller class only not service class, error:
	//No value at JSON path "&[0].showId" as not api found but since api is not being called from service so no data found
	//still normally starting as we are not calling service
	
	//after I called all the methods from service class in the controller class, but did not write any annotation in the service class
	//after this all test cases red marked, error in all test cases, jo 3 login ke pass ho rhe the wo bhi nhi chale ab to
	//Illegal state exception only.. test case ke overall log me kuch dikha hi nhi rha bas yhi exception
	//each test case log me bhi yhi dikha rha: 
	
	//java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: 
	//skipping repeated attempt to load context for [WebMergedContextConfiguration@1013aa94 testClass = com.example.
	//Innovator2025June28.Innovator2025June28ApplicationTests, locations = [], classes = [com.example.Innovator2025June28.
	//Innovator2025June28Application], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], 
	//propertySourceProperties = ["org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true"], 
	//contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@4e31276e, org.
	//springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5117dd67, 
	//org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.
	//TestRestTemplateContextCustomizer@5b7a8434, org.springframework.boot.test.web.reactor.netty.
	//DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@418c5a9c, 
	//org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@13d4992d, 
	//org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f,
	//org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@2d6c53fc, 
	//org.springframework.test.context.support.DynamicPropertiesContextCustomizer@0, org.springframework.boot.test.context.SpringBootTestAnnotation@28b5cf5d], resourceBasePath = "src/main/webapp", 
	//contextLoader = org.springframework.boot.test.context.SpringBootContextLoader, parent = null]

	//jab normally run kiya to ye error aaya:
	//Consider defining a bean of type 'com.example.Innovator2025June28.service.ShowService'
	//then i checked ki maine service class me ek bhi annotation nhi likhe the, 
	//@Service nhi likha tha : Illegal state exception
	
	//same yhi error aa rhi thi sabse phle test case run kiya tha maine to.. kyunki pom.xml me dependency nhi download huyi thi
	//Project likha rhta hai in pom.xml ke andar.. uss par under line red color me.. jabki bahar kahin bhi koi red cross nhi tha
	//na hi main project ke upar na hi pom.xml ke upar
	//force download par click kiya jo aaya tha uske par mouse hover krne se to shi hua tha..
	//update maven project se kuch nhi hua, install.sh file run krne se bhi kuch nhi hua
	//but issi ke baad se lgta hai ki jwt token wali error aayio thi
	
	
	//sab kuch sahi krne ke baad, ab get data par error aa rhi thi: No value at JSON path "&[0].showId"
	//kyunki maine bas service annotation lagaya tha kopi find all code nhi likha tha, so api was not finding any data from db
	//to bas abhi to 3 test case pass ho rhe atleast login wale, signUp fail rha kyunki test case galat hai
	
	//ab finally station add par ye error aa rhi : Cannot invoke "String.startsWith(String)" because "token" is null
	//ye red mark hai kyunki abhi koi code nhi likha iska service class me
	//even after writting try catch basic code but no db call, same error but it should be 400 if any issue in try
	//there after code, i was getting error JwtSignature does not match locally computed jwt signature
	
	//yha phle error aa rhi thi ki temp.txt could not be created while ran test case.. abh shayad read nhi kr pa rha..
	//may be some issue in save data and get data from file code in test case, nope, checked with last year code, it is correct
	
	//actually i had written here : token.startWith("Bearer ") instead of tokenHeader , so it was null
	//yad rjhna ye error bhi
	
	//now getting : io.jsonwebtoken.UnsupportedJwtException: Unsigned Claims JWTs are not supported. for stationAdd test case
	//even i have written parseClaimJws this time
	//i missed to write signWith, so this error came, sam eerror if write parseClaimsJwt along with signWith
	
	//so finally issue resolved
	
	//token is saved in temp.txt file: so if any token error, check here once
	//{"token_admin_2":"eyJhbGciOiJub25lIn0.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTEyOTg2MzMsImV4cCI6MTc1MTMwMDQzM30.",
	//"token_admin_1":"eyJhbGciOiJub25lIn0.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTEyOTg2MzMsImV4cCI6MTc1MTMwMDQzM30.",
	//"token_user_1":"eyJhbGciOiJub25lIn0.eyJzdWIiOiJTYW0iLCJpYXQiOjE3NTEyOTg2MzMsImV4cCI6MTc1MTMwMDQzM30."}
	
	//new token:
	//{"token_admin_2":"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTEzMDA2NDEsImV4cCI6MTc1MTMwMjQ0MX0.aQZUYm7qG-S1NvKR8KVORnZ0DBGD4xAnMDNS0pnFd1qjy8SEKTJIXKdRzji9L7yq",
	//"token_admin_1":"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTEzMDA2NDEsImV4cCI6MTc1MTMwMjQ0MX0.aQZUYm7qG-S1NvKR8KVORnZ0DBGD4xAnMDNS0pnFd1qjy8SEKTJIXKdRzji9L7yq",
	//"token_user_1":"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJTYW0iLCJpYXQiOjE3NTEzMDA2NDEsImV4cCI6MTc1MTMwMjQ0MX0.oqPkseDixx_8UMBEQSlCQYVgrHaDRv0-33T1jGZWiovSK7U06LrWKrJzXeUiy9e4"}

	
	 
	//Error: email null first, primary key or unique constraint violation
	//this was confusing due to email nulls first but email was not null, it was unique but test case was trying to insert same email again
	@PostMapping("/signUp")
	public ResponseEntity addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}
	
	
	//Error: JWT strings must contain exactly 2 period characters. Found: 0
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit login error");
		}
		
		//2025 June mistake.. I did mistake here.. I called extractUsername() here instead of generateToken here
		//JWT strings must contain exactly 2 period characters. Found: 0 
		//getting same mistake for login api, agar ye na hota to mera ho jata kyunki mai baki sari api kar leta..
		//signup unki mistake hai.. but issi signup ki wajah se hoi to confusion ho ki kaha se aa rhi error..
		//ekdam strange error.. to laga ki usi signup ki wajah se ye mistake ho rhi.. user sahi nhi hai shayad..
		//jab login api me error aa rha tha to wahi dekhna tha ek baar, neeche line number 163 bhi diya hai jaha mistake huyi hai
		//ye mistake na krta mai to mera sara ho jayta waise..authRequest baki sari apis mai kar leta..
		/*
		Caused by: io.jsonwebtoken.MalformedJwtException: JWT strings must contain exactly 2 period characters. Found: 0
	at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:275)
	at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:529)
	at io.jsonwebtoken.impl.DefaultJwtParser.parseClaimsJws(DefaultJwtParser.java:589)
	at io.jsonwebtoken.impl.ImmutableJwtParser.parseClaimsJws(ImmutableJwtParser.java:173)
	at com.example.Innovator2025June28.service.JwtService.exctractAllClaims(JwtService.java:61)
	at com.example.Innovator2025June28.service.JwtService.extractClaim(JwtService.java:56)
	at com.example.Innovator2025June28.service.JwtService.extractUsername(JwtService.java:48)
	at com.example.Innovator2025June28.controller.LoginController.authenticateAndGetToken(LoginController.java:163)
	yaha wo line number bhi bata rha tha but mai itna nervous ho gya tha ki kuch samajh hi nhi aa rha tha..
	
	Login api me bhi to ham token generate krte hain na.. to ek baar yaha dekhna tha..
	mai bas jwtService dekh rha tha and jwtFilter dekh rha tha.. kyunki waha ham token generATE KRTE Hain from tokenHeader,
	jo authentication header me pass hota hai and filter is APPLIED before running any api..
		 */
		
		//log dekh leta dhyan se.. login api me error to phle loginController:163 line dikha rha tha waha jata to pata chal jata
		//jis api me error aa rhi.. log me sabse phle wo class khojo..
		//ek baar debug kr leta login api kyunki error issi me aa rhi thi.. but maine wo bhi nhi kiya.. 
		//errors iotni dekhane ke baad kuch samajh hi nhi aa rha tha.. mere paas 1 hour tha waise..
		//nut lag yhi rha tha ki signUp ya jwt token ki wajah se kuch issue hai..
		//jis api me error aa rhi hai wahi api sabse phle debug karo.. wo khud hi le jayega uss error par
		
		//be very carefull.. token 3 jagah generate hota hai.. jwtSevice me generation ka code
		//authFilter me token nikalte hain from tokenHeader
		//login api me token generate krte hain using jwtService method generate token
		//String token = jwtService.extractUsername(authRequest.getUsername());
		
		String token = jwtService.generateToken(authRequest.getUsername());
		
		//now add station is giving below error:
		//io.jsonwebtoken.ExpiredJwtException: JWT expired at 2025-06-30T16:54:01Z. Current time:
		//2025-07-05T04:32:40Z, a difference of 387519318 milliseconds.  
		//Allowed clock skew: 0 milliseconds.
		//ye error aati hai jab token sahi se pass nhi hota.. matlab header me na paas kiya ho..
		//ya login me generate kiya ho but wo hi galat generate hua ho..
		//means if token is not correct to if check expiration, it will give expired
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
	}

}
