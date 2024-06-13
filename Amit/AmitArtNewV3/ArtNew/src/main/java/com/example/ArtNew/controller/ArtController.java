package com.example.ArtNew.controller;

import com.example.ArtNew.dtoDataTransferObject.JwtRequest;
import com.example.ArtNew.model.ArtModel;
import com.example.ArtNew.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artWork")
public class ArtController {

    @Autowired
    ArtService artService;

    //'getArtByMedium(java.lang.String)' is not public in 'com.example.ArtNew.service.ArtService'.
    //Cannot be accessed from outside package, this was default

//    @GetMapping("/list")
//    ResponseEntity<Object> getAllArt(){
//        return null;
//    }

    @GetMapping("/list")
    public ResponseEntity<Object> getArtByMedium(@RequestParam("medium") String medium){

        return artService.getArtByMedium(medium);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> saveArt(@RequestBody ArtModel artModel){
        return artService.saveArt(artModel);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateArt(@PathVariable int id, @RequestBody ArtModel artModel){
        return artService.updateArt(id, artModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteArt(@PathVariable int id){
        return artService.deleteArt(id);
    }
}
