package com.example.Innovator2025June28.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Innovator2025June28.entity.Show;
import com.example.Innovator2025June28.entity.Station;
import com.example.Innovator2025June28.entity.UserInfo;
import com.example.Innovator2025June28.repository.ShowRepo;
import com.example.Innovator2025June28.repository.StationRepo;
import com.example.Innovator2025June28.repository.UserRepository;

@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ShowRepo showRepo;
	
	@Autowired
	StationRepo stationRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		UserInfo userInfo1 = userRepository.save(new UserInfo("Dev", "dev@gmail.com", passwordEncoder.encode("pass1"), "ADMIN"));
		UserInfo userInfo2 = userRepository.save(new UserInfo("Justin", "justin@gmail.com", passwordEncoder.encode("pass2"), "ADMIN"));
		UserInfo userInfo3 = userRepository.save(new UserInfo("Sam", "sam@gmail.com", passwordEncoder.encode("pass3"), "USER"));
		
		Station station1 = stationRepo.save(new Station("RadioWave FM", "101.2 FM", "Pop", "English", "USA", "https://radiowavefm.com/stream", true, "06:00 AM", "11:00 PM", userInfo1.getId()));
		Station station2 = stationRepo.save(new Station("Global Beats", "101.3 FM", "Western", "French", "France", "https://globalbeates.fr/stream", false, "05:00 AM", "10:00 PM", userInfo2.getId()));

		Show show1 = showRepo.save(new Show("Morning Vibes", "refreshing morning show with great music and positive talks", "08:00 AM - 10:00 AM", 120, "Emma Roberts", "Music & Talk", 5, station1.getStation_id()));
		
		
		//2025 june 28 mistakes summary:
		
		//jais ehi koi  class ka cod elikhte ho 2 min me ek baar poora code recheck karo.. jhar ek line..
		//last time bhi socha tha but kiya nhi iss baar ye.. same mistake happened.. imported wrong class/called wrong method
		
		//Most imp point.. tum chaho to 10 min me poora security code revise kr skte ho ek baar ki sab kuch sahi likha hai ki nhi..
		//but maine dono exam me ek baar bhi poora security code dobara nhi dekha.. jabrdasti logs ke peeche pada tha..
		//ek baar bhi maine dobar dekha hota code to aaj paper nikal gya hota mera.. pata nhi kya ho jata hai papapr me ki dobara
		//code dekhna hi nhi hai..
		
		//0. signUp gave emails nulls first then said primary key or unique violation, this is confusing emails nulls first
		//but actually tried to enter same username and email again so this error as these are unique
		
		//1. in pom.mxl, error at Project due to dependency not download... giving Illegal state exception.. nothing from debug
		//just hover mouse over project in the pom.xml and click once on force download, error will be gone
		
		//2. in login api, instead of JwtService.generateToken, I called getUsernameFromToken..
		//error. token ex[ected 2 period value means 2 dots but found none.. means token is not correct formate.
		//as we stored username in the token so no dots in the jwt token
		//header.payload.signature  so 2 dots required here..
		//other apis giving error: Jwt signature does not match local computed jwt signature. as ibn actual jwt we have 2 dots
		//but here in local computed jwt token, there is no dot as we stored username in the token
		//again same mistake that imported wrong class.. eclipse dikhta hi nhi kuch.. so next time in intellij in light theme
		//if any query, write in the eclipse then.. but other things do in the intellij, it is light as well
		//so it is advise to trim before token substring and also after token is generated in jet filter
		//to remove extra white spaces..
		//no space should be in jwt secret as well
		
		//total 3 places where Jwt token code is written.. jwtService, JwtFilter, login api where token = jwtService.generateToken()
		//must be very careful at these places.
		
		//3. in JwtFilter, if used token.subString(7) instead of tokenHeader.subString(7).. will give token is null
		
		//4. in jwt Service, if write, parseClaimsJwt instead of parseCLaimsJws.. will give can not parse unSigned jwt token
		//if write parseCLaimsJws but miss signWith() then also same error. unSupported jwt token
		
		//5. after writing security codes, only sign up and login api will work and no other api will work as 
		//we have not written any annotation on rest controller and service for main api.. and we have not called findAll yet
		//so even findAll api will fail.. as even no token is required for these public apis.. but still it will not work as no code yet
		
		//6. if write only rest controller all annotations and nothing on service class, then error service bean not found..
		//but problem is on running tyest case, nothing will tell, even debug will tell nothing.. when normally run code 
		//then says could not start as service bean not found
	}

}
