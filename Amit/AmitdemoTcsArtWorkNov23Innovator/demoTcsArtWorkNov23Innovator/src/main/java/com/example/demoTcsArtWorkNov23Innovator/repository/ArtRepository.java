package com.example.demoTcsArtWorkNov23Innovator.repository;

import com.example.demoTcsArtWorkNov23Innovator.model.ArtModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<ArtModel, Integer> {
    //add required annotation to make the art repository
}
