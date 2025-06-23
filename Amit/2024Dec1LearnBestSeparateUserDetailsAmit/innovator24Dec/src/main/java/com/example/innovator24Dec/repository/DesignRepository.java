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
	
	//@Query(nativeQuery = true, value = "select p.category_id, p.price, p.product_id, p.seller_id, p.product_name from Product p, Category C where p.category_id = c.category_id and upper(p.product_name) like upper('%' || ?1 '%') or upper(c.category_name) like upper('%' || ?2 || '%') ")
	//List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameIgnoreCaseContaining(String productName, String categoryName);
	

	
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

//------------------what to do if not working anything in exam and no idea where is the error coming from------------
//first check in test case logs go to end first and scroll right most end, there you will get the error why failing

//ek baar waha bhi check kar lo jaha main complete logs hota hai not in test cases separate logs.. wha sari error print hoti hai
//as debug se itna pata chala ki save call nhi ho rha.. but kyu8 nhi call ho rha pata nhi chal rha tha..
//then in main console checked.. default constructor was not present for entity class so save was not calling

//must write e.printStackTrace() in catch case in api services, so that logs will be printed with 400 otherwise just 400

//debug the test case when logs se samajh nhi aa rha.. kuch samajh nhi aa rha ki error kya hai only getting 400
//was geting 400 for getAll() api, kuch samajh nhi aa rha.. then debug the test case.. to pata chala ki security context holder
//se turnt 400 de rha.. then pata chala ki authentication nhi pass hota in public api so it security code will give error



//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//if writting above line in the code, giving 400 as get/list api is public so no authentication provided there
//so this line gives some exception so bad request is returned
//all public apis giving this error if write this line there


//----------------------points to care in exam---------------------
//in exam, if you will select workspace inside prject, then when you will try to open project from eclipse, it will not open
//always make workspace as DEsktop or leave whatever is there as default.
//when opening project, then select project folder only
//---------




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