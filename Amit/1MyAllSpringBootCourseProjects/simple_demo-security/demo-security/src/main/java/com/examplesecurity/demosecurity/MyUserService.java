package com.examplesecurity.demosecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    MyUserRepository myUserRepository;

    @Override //given a user name return user details of that user.. userDetails is again an interface with username, password.. this userDetail is implemeneted by user class
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //given a user name return user details of that user.. userDetails is again an interface with username,
        // password.. this userDetail is implemeneted by user class
        //it does not matter that you return user details from mysql, mongodb or any db
        //this method will be called internally we do not need to that.. fetching user details and matching password is
        //not our concern.. done internally
        //we can not write @Entity over User class so we ned to write our own user class that implements this User class

        return (UserDetails) myUserRepository.findByMyUserName(username);
    }

    public MyUser createUser (MyUser myUser){
        return myUserRepository.save(myUser);
    }
}
