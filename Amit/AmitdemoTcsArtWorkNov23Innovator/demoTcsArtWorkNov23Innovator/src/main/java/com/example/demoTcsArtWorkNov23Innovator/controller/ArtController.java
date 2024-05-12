package com.example.demoTcsArtWorkNov23Innovator.controller;

import com.example.demoTcsArtWorkNov23Innovator.model.ArtModel;
import com.example.demoTcsArtWorkNov23Innovator.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artWork")
public class ArtController {
    @Autowired
    ArtService artService;

    @PostMapping("/add")
    public ResponseEntity<Object> postArtWork(ArtModel artModel){
        return null;
    }

    @GetMapping("/list")
    public List<ArtModel> getArtModelList(){
        return null;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateArtWork(int id, ArtModel artModel){
        //for update Patch is used
        return null;
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<Object> deleteArtWork(int id){
        return null;
    }
}
