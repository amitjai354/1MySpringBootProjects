package com.example.innovator24Dec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.entity.UserInfo;
import java.util.List;


@Repository
public interface DesignRepository extends JpaRepository<Design, Integer> {
	
	List<Design> findByPrice(int price);

	List<Design> findByDesignIdAndAvailabilityStatusEqualsAndPriceLessThanEqual(int id, String availabiltyStatus, int price);
}
