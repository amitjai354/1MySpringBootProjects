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
    
    ArtService artService;

//    {
//        "title":"Mona Lisa",
//        "description":"One of the most famous portraits in the world",
//        "artist":"Leonardo da vinci",
//        "medium":"Oil on panel",
//        "price":1800000.0,
//        "dimensions":"77 cm * 53 cm(30 in * 21 in)",
//        "imageLink":"https://example.com/artworks/mona-lisa.jpg"
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> postArtWork( ArtModel artModel){
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getArtModelList(String medium){
        return null;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateArtWork( int id, ArtModel artModel){
        //for update Patch is used,also Put is used but in Patch send only those fild which we need to modify
        //in put we send complete object and if object not in db saves this as new object
        //but in patch object must be existinbg in th db
        return null;
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<Object> deleteArtWork( int id){
        return null;
    }
}
