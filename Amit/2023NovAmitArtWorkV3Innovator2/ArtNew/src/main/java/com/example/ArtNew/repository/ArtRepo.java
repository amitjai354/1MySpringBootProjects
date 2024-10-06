package com.example.ArtNew.repository;

import com.example.ArtNew.model.ArtModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtRepo extends JpaRepository<ArtModel, Integer> {
    List<ArtModel> findByMedium(String medium);
}
