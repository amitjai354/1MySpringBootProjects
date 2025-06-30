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

		Show show1 = showRepo.save(new Show("Morning Vibes", "refreshing morning show with great music and positive talks", "08:00 AM - 10:00 AM", 120, "Emma Roberts", "Music & Talk", 5, station1.getStation_Id()));
		
		
		
	}

}
