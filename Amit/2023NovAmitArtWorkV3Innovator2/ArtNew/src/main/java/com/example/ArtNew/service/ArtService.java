package com.example.ArtNew.service;

import com.example.ArtNew.model.ArtModel;
import com.example.ArtNew.model.UserModel;
import com.example.ArtNew.repository.ArtRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtService {

    //1:35 min

    @Autowired
    ArtRepo artRepo;

//    ResponseEntity<Object> getAllArt(){
//        return null;
//    }


    public ResponseEntity<Object> getArtByMedium(String medium){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ArtModel> artModelListFromDb = artRepo.findByMedium(medium);
        if(artModelListFromDb.isEmpty()){
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("not found");
        }
        return ResponseEntity.ok(artModelListFromDb);
    }


    public ResponseEntity<Object> saveArt(ArtModel artModel){
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            artModel.setUserModel(userModel);
            artModel.setOwnerId(userModel.getId());
            artModel=artRepo.save(artModel);
            return ResponseEntity.ok(artModel);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in saving art");
        }

    }


    public ResponseEntity<Object> updateArt(int id, ArtModel artModel){
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ArtModel artModelFromDb=artRepo.findById(id).orElse(null);
            if(artModelFromDb==null){
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("not found");
            }
            if (artModelFromDb.getUserModel().getId()!= userModel.getId()){
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("You don't have permission");
            }
            artModelFromDb.setPrice(artModel.getPrice());
            artModelFromDb.setAvailable(true);
            artModelFromDb=artRepo.save(artModelFromDb);
            return ResponseEntity.ok(artModelFromDb);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in updating art");
        }

    }


    public ResponseEntity<Object> deleteArt(int id){
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ArtModel artModelFromDb=artRepo.findById(id).orElse(null);
            if(artModelFromDb==null){
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("not found");
            }
            if (artModelFromDb.getUserModel().getId()!= userModel.getId()){
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("You don't have permission");
            }

            artRepo.deleteById(id);
            return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in deleting art");
        }

    }
}
