package com.example.AmitSbTcsJwtV3Project.controller;

import com.example.AmitSbTcsJwtV3Project.config.JwtUtil;
import com.example.AmitSbTcsJwtV3Project.model.JwtRequest;
import com.example.AmitSbTcsJwtV3Project.model.Product;
import com.example.AmitSbTcsJwtV3Project.repository.ProductRepo;
import com.example.AmitSbTcsJwtV3Project.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PublicController {

    @Autowired
    UserAuthService myUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/product/search")
    public List<Product> getProducts(@RequestParam("keyword") String keyword){
        return productRepo.findByProductNameIgnoreCaseContainingOrCategoryCategoryNameIgnoreCaseContaining(keyword.toUpperCase(), keyword.toUpperCase());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtRequest jwtRequest){
        //since passing username password to verfify user and generate tokenin jwt request so use UsenamePaswordAuthenticatiionToken
        // and pass username , password in it
        //then authticate useing authenticationManager bean that we created

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword());
        try{
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        //org.springframework.security.authentication.InternalAuthenticationServiceException:
        // UserDetailsService returned null, which is an interface contract violation
        //this exception is thrown if username is wrong while trying to generate token
        //if password wrong then BadCredentials exception
		
		//Spring Security hides the username does not exist exception by wrapping it so that when developing,
        // developers only get BadCredentialsException
        //when we throw username not found exception then Spring wraps it and returns Bad credential
        //as if we say username not found , hacker will know surely that he need to change username only
        //if (userModel==null){
        //  throw new UsernameNotFoundException("Username not found");
        //}
        //when returning username not found we are getting 500 bad request:  in load user by username code
		
        //when password is incorrect then Bad credentials
        //but if username is wrong then UserNameNotFoundException
        //so global error handlinhg: invalid credentials working only when password is wrong
        //but if username wrong then userDetailsService returns null, this is UsernameNotFound
        //so better at end also give Exception e
        //Spring Security hides the username does not exist exception by wrapping it so that when
        // 4developing, developers only get BadCredentialsException
        catch (BadCredentialsException e){
            e.printStackTrace();//only prints on screen but does not stop program by throwing any exception
            throw new BadCredentialsException("Username or Password is incorrect");
            //this error show in console only not in api so need ExceptionHandler
        }
        catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new UsernameNotFoundException("Username is wrong");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }
        //once authenticated just return token..
        //setting authentication is done in onceperrequest filter
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        //return ResponseEntity.ok(token);
        //return ResponseEntity.ok().body(token);
        //return  new ResponseEntity<>(token, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}

//@Component
//public class DataLoader implements ApplicationRunner {
//    //run vs main method, main is static method so can not use autowire here
//    //this is better than data.sql.. many problems there, name should be same, need to create new file
//    //we have to write complete query by ourself that may be wrong and will consume time in exam
//    //need to check everything properly like table name, column name.. otherwise multiple errors
//    //but here just call hibernate jpa save method, nothing else to do
//    //everytime program starts, this run () will execute and dat will be inserted
//
//    //CommandLineRunner vs ApplicationRunner, both behave same way , are excuted just before
//    //Springbootapllication.run completes
//    //only difference is that command line run take string as argument
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        roleRepository.save(new RoleModel("Owner"));
//        roleRepository.save(new RoleModel("Customer"));
////        RoleModel role1=roleRepository.save(new RoleModel("Owner"));
////        RoleModel role2=roleRepository.save(new RoleModel("Customer"));
//
//        userRepository.save(new UserModel( 1,"owner1", "owner123$", "owneremail@gmail.com",roleRepository.findById(1).orElse(null)));
//        userRepository.save(new UserModel(2,"owner2","owner789$","owneremail2@gmail.com",roleRepository.findById(1).orElse(null)));
//        userRepository.save(new UserModel(3,"customer","customer123$","customeremail@gmail.com", roleRepository.findById(2).orElse(null)));
////        UserModel user1=userRepository.save(new UserModel( "owner1", "owner123$", "owneremail@gmail.com",role1));
////        UserModel user2=userRepository.save(new UserModel("owner2","owner789$","owneremail2@gmail.com",role1));
//
//        //may be problem is that this will be commited when all executed, som currently
//        // role model is null, so giving foreign key constrain as fk not exist, nut fk can be null
//        //if i am removing user id, then also same error
//        // so either pass this as null may be later on jpa will create relation
//
//        //if save role2 then working.. problem is with @OneToOne
//        //means one role will be attached to one user only but we are trying to attach role1 to 2 users
//        //then getting this error
//        //on making it @ManyToOne, this is working now, np in code here
//        //	//here i user can be both customer and seller if many to many using,
//        //	many to many means one user has many roles and one role has many users
//        //Many to one means many user can have one role/one role can have many users
//        //but one user can not have many roles, he can not be both owner and customer
//    }
//}