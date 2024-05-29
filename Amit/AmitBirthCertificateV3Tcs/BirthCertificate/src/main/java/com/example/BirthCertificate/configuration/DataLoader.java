package com.example.BirthCertificate.configuration;

import com.example.BirthCertificate.model.RoleModel;
import com.example.BirthCertificate.model.UserModel;
import com.example.BirthCertificate.repository.RoleRepository;
import com.example.BirthCertificate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RoleModel roleModel1 = roleRepository.save(new RoleModel("DOCTOR"));
        RoleModel roleModel2 = roleRepository.save(new RoleModel("MEDICALOFFICER"));

        userRepository.save(new UserModel("doctor1", "doctor123$", "doctor@gmail.com", roleRepository.findById(1).orElse(null)));
        userRepository.save(new UserModel("doctor2", "doctor789$", "doctor2@gmail.com", roleModel1));
        userRepository.save(new UserModel("medicalOfficer", "medicalOfficer123$", "medicalOfficer@gmail.com", roleModel2));
    }
}
