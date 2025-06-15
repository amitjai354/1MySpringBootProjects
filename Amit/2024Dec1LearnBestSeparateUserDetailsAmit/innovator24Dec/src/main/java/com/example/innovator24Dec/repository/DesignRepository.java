package com.example.innovator24Dec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innovator24Dec.entity.Design;

@Repository
public interface DesignRepository extends JpaRepository<Design, Integer> {
	//List<Design> findByAvailabilityStatusAndPriceLessThanEqual(String availabilityStatus, int price);
	
	//Optional<Design> findByIdAndAvailabilityStatusEqualsAndPriceIsLessThanEqual(int id, String avalabiltyStatus, int price);
	//giving error as i used id but in Design we have: designId
	
	Optional<Design> findByDesignIdAndAvailabilityStatusEqualsAndPriceIsLessThanEqual(int id, String avalabiltyStatus, int price);
	//this query is working perfectly fine, but i did same with java as well in service
	
	
	//write cart api codes as well here
	
	List<Design> findByPrice(int price);
}


/*
-------how to debug test cases----
*/