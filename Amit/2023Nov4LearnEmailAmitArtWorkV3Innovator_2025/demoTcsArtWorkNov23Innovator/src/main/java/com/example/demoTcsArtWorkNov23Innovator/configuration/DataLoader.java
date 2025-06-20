package com.example.demoTcsArtWorkNov23Innovator.configuration;

import com.example.demoTcsArtWorkNov23Innovator.model.RoleModel;
import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.RoleRepository;
import com.example.demoTcsArtWorkNov23Innovator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements ApplicationRunner {
    //run vs main method, main is static method so can not use autowire here
    //this is better than data.sql.. many problems there, name should be same, need to create new file
    //we have to write complete query by ourself that may be wrong and will consume time in exam
    //need to check everything properly like table name, column name.. otherwise multiple errors
    //but here just call hibernate jpa save method, nothing else to do
    //everytime program starts, this run () will execute and dat will be inserted

    //CommandLineRunner vs ApplicationRunner, both behave same way , are excuted just before
    //Springbootapllication.run completes
    //only difference is that command line run take string as argument

	
	@Autowired
    RoleRepository roleRepository;

	@Autowired
    UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //may be problem is that this will be commited when all executed, som currently
        // role model is null, so giving foreign key constrain as fk not exist, nut fk can be null
        //if i am removing user id, then also same error
        // so either pass this as null may be later on jpa will create relation

        //if save role2 then working.. problem is with @OneToOne
        //means one role will be attached to one user only but we are trying to attach role1 to 2 users
        //then getting this error
        //on making it @ManyToOne, this is working now, np in code here
        //	//here i user can be both customer and seller if many to many using,
        //	many to many means one user has many roles and one role has many users
        //Many to one means many user can have one role/one role can have many users
        //but one user can not have many roles, he can not be both owner and customer
    	
    	RoleModel roleModel1 = roleRepository.save(new RoleModel("OWNER"));
    	RoleModel roleModel2 = roleRepository.save(new RoleModel("CUSTOMER"));
    	
    	UserModel userModel1 = userRepository.save(new UserModel("owner1", passwordEncoder.encode("owner123$"), "owneremail@gmail.com", roleModel1));
    	UserModel userModel2 = userRepository.save(new UserModel("owner2", passwordEncoder.encode("owner789$"), "owneremail2@gmail.com", roleModel1));
    	UserModel userModel3 = userRepository.save(new UserModel("customer", passwordEncoder.encode("customer123$"), "customeremail@gmail.com", roleModel2));

    }
}
