package com.example.innovator24Dec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innovator24Dec.entity.Design;

@Repository
public interface DesignRepository extends JpaRepository<Design, Integer> {
	
	List<Design> findByPrice(int price);
	
	//List<Design> findByAvailabilityStatusAndPriceLessThanEqual(String availabilityStatus, int price);
	
	//Optional<Design> findByIdAndAvailabilityStatusEqualsAndPriceIsLessThanEqual(int id, String avalabiltyStatus, int price);
	//giving error as i used id but in Design we have: designId
	
	Optional<Design> findByDesignIdAndAvailabilityStatusEqualsAndPriceIsLessThanEqual(int id, String avalabiltyStatus, int price);
	//this query is working perfectly fine, but i did same with java as well in service
	
	
	/*
	//write cart api codes as well here
	
	---------------------------------------
	Product Repo:
	List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName, 
			String categoryName);
			
	findBySellerUserIdAndProductId(int userId, int productId);
			
	---------------------------
	CartProduct Repo: 
	
	@Transactional
	void deleteByCartUseruserIdAndProductProductId(Integer userId, Integer productId);
	
	//when more than one entity is involved so always use @Transactional otherwise will give error..
	 as it may cause inconsistency in data
	 
	 deleteById here no need to use @Transactional
	
	----------------------------------------------
	In CartRepo: findByUserUserId(int userId); it can be findByCartUserUserId(int userId);
	
	------------------------------------------------
	
	*/
	
}


/*
-------how to debug test cases----
put debug point at start of test case in test case java file..
then put debug in controller class on the api that is being called in this test case eg /design/add
then put debug in the service class where code for api is written..

now run debug but not the normal debug but test case one debug..

control will go to test case.. now same as normally we debug..
*/


/*
GenerationType : IDENTITY, AUTO, SEQUENCE, TABLE, UUID added now
IDENTITY: incremented by DB for each table
SEQUENCE: maintains a sequence and updates id at overall all the tables
TABLE: maintains a separate table for sequence
UUID: creates uuid as pk
AUTO: Based on db and type of pk, automatically selects either identity, or sequence or uuid
*/