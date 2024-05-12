package com.example.demoTcsArtWorkNov23Innovator.service;

import com.example.demoTcsArtWorkNov23Innovator.model.ArtModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.ArtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArtService {

    @Autowired
    ArtRepository artRepository;

    @Autowired
    UserService userService;

    //created 201 and bad request 400
    public ResponseEntity<Object> postArtWork(ArtModel artModel){
        return null;
    }

    //200 and 400
    public ResponseEntity<Object> getArtWork(String medium){
        return null;
    }

    //to update art work price and available status
    //only the owner of that particular artwork can update
    //200, 400, 403 forbidden
    public ResponseEntity<Object> updateArtWork(int id, ArtModel artModel){
        return null;
    }

    //to delete artwork by using id
    //only the owner of that particular artwork can update
    //no content 204 and bad request 400 and forbidden 403
    public ResponseEntity<Object> deleteArtWork(int id){
        return null;
    }

}
