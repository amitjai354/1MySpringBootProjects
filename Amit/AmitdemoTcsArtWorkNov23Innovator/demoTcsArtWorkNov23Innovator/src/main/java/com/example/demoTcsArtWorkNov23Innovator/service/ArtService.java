package com.example.demoTcsArtWorkNov23Innovator.service;

import com.example.demoTcsArtWorkNov23Innovator.model.ArtModel;
import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.ArtRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtService {

    @Autowired
    ArtRepository artRepository;

    @Autowired
    UserService userService;

    //created 201 and bad request 400
    public ResponseEntity<Object> postArtWork(ArtModel artModel){
        try{
            //String email1=SecurityContextHolder.getContext().getAuthentication().getName();//owner1
            //UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserModel userModel1= (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //Both userDetails and userModel has email in debug mode
            //But userdetails is not giving getEmail while we have getter in UserModel which implements userdetails
            //since UserModel implements userDetails so this is also UserDetails, here we will get getEmail()
            //We can get all userDetails from Security context
            //not using findByusername because it is not unique, currently this is unique so it will work also
            //but if later omn some user comes with same username then problem will be there
            String email=userModel1.getEmail();
            //here also returning username not email
            //but in load user by username in tcs coe String email passing
            //how willl we get email, userbname is not unique here

            //We got userModel from Security context but below one is preffered way to get userModel
            //so taking email only from above usermodel
            //Lect 17 1:22, getPrincipal returns Proxy only, he is taking UserModel object from SecurityContext
            //by casting the getPrincipal.. but he is taking id from here then calling findById
            //actuall user object is actual but if some book list or anything inside thart is proxy..
            //so be on safer side after getting from security contextr, call db query, he said in Lect 17 1:40:00
            //this is completely correct, ibn Lecture dopsing same way, typecasting first then using
            //not taking username directly
            UserModel userModel=userService.getUserByEmail(email);
            artModel.setOwnerId(userModel.getId());
            artModel.setAvailable(true);
            artModel=artRepository.save(artModel);
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(artModel);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Bad request was made");
        }
    }

    //200 and 400
    public ResponseEntity<Object> getArtWork(String medium){
        try{
            List<ArtModel> artModelListByMedium = artRepository.findByMedium(medium);
            if (artModelListByMedium.isEmpty()){
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data available");
            }
            return ResponseEntity.ok(artModelListByMedium);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }
        //need to uise hasAnyAuthority("Owner","Customer") here other wise if permit all then any one can see
        // other customer and owner any one can see
        //login is permit all, first login as customer t5hen see art list

    }

    //to update art work price and available status
    //only the owner of that particular artwork can update
    //200, 400, 403 forbidden
    public ResponseEntity<Object> updateArtWork(int id, ArtModel artModel){
        ArtModel artModelFromDb = artRepository.findById(id).orElse(null);
        if (artModelFromDb==null){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
        }
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (artModelFromDb.getOwnerId()!=userModel.getId()){
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
        }
        artModelFromDb.setAvailable(artModel.isAvailable());
        artModelFromDb.setPrice(artModel.getPrice());
        //artModelFromDb=artRepository.save(artModel);
        artModelFromDb=artRepository.save(artModelFromDb);
        return ResponseEntity.ok(artModelFromDb);
        //you can do has AnyAuthority(owner, customner) in requst Matcher and if id does not match
        // then snd this msg you dont have permission
        //but if doing hasAuthority(Owner) only then you will get Forbidden from there for customer
        //thois msg will not go there

    }

    //to delete artwork by using id
    //only the owner of that particular artwork can update
    //no content 204 and bad request 400 and forbidden 403
    public ResponseEntity<Object> deleteArtWork(int id){
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //will get class cast exception here if token not passed in header and security configuration not
            //defined properly or permit all, if properly written request matcher then 403 forbidden
            String email = userModel.getEmail();
            UserModel userModelFromDb = userService.getUserByEmail(email);
            ArtModel artModelFromDb = artRepository.findById(id).orElse(null);
            if (artModelFromDb==null){
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
            }
            if(artModelFromDb.getOwnerId()!=userModelFromDb.getId()){
                return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
            }
            artRepository.deleteById(id);
            return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
            //204 is not error, this means successfukly deleted 2xx is successful, 4xx is error
            //204 successfuly No content means does not return anything in response body
            //it discards response body
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }

    }

}
