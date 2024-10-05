package com.example.ArtNew.securityConfiguration;

import com.example.ArtNew.model.RoleModel;
import com.example.ArtNew.model.UserModel;
import com.example.ArtNew.repository.RoleRepo;
import com.example.ArtNew.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RoleModel roleModel1 = roleRepo.save(new RoleModel("OWNER"));
        RoleModel roleModel2 = roleRepo.save(new RoleModel("CUSTOMER"));

        UserModel userModel1 = userRepo.save(new UserModel("owner1", passwordEncoder.encode("owner123$"), "owneremail@gmail.com", roleModel1));
        UserModel userModel2 = userRepo.save(new UserModel("owner2", passwordEncoder.encode("owner789$"), "owneremail2@gmail.com", roleModel1));
        UserModel userModel3 = userRepo.save(new UserModel("customer", passwordEncoder.encode("customer123$"), "customeremail@gmail.com", roleModel2));


    }
}
