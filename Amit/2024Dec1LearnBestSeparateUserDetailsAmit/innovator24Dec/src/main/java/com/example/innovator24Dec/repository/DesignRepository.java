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



//----====================================================================================================

//====================VERY Important POINTS--====================================================

//Most imp.. waht approach to solve in exam.. 
//First write all security code along with sign up, login, data loader in 1 hr.. except api code and controller code for api..
//if hav not autowired this main api controller and service then will not give any error
//if have autowired somewhere then must write all annotations in controller and service for api class
//then ek baar normal run krke dekh lo ki start ho rha ki nhi.. then run test cases.. around 5 test cases will pass
//if any issue comes.. to time rahega dekhane ka.. debug kr lo.. logs dekh lo.. na samajh aaye kuch to sara code ek baar ache se dekh lo mistype to nhi hua..
//likhte time hi bhot dhyan se likhna.. kya import kr rhe wo dikhta nhi.. to import ke baad check jaroor krna
//last time mai jaldi jaldi import krta chala gya and cross check bhi nhi kiya..
//waha ke eclipse me dikhta bhi nhi.. kya import ho tha.. try krna if theme change ho jaye to,,

//intellij is not good.. code complete  nhi krta wo, eclipse jyada ache se krta hai.. like if you have map attribute 
//then eclipse will fill it if you are calling any method where map is needed but intellij me sara kuch hame hi input dena hota hai



//--==============================================================================================================
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



//-===============================================================================
/*
-------how to debug test cases----
put debug point at start of test case in test case java file..
then put debug in controller class on the api that is being called in this test case eg /design/add
then put debug in the service class where code for api is written..

now run debug but not the normal debug but test case one debug..

control will go to test case.. now same as normally we debug..

Most imp thing is: when after error, control goes to catch Block, hover mouse over e.printStacktrace mainly e
it will give the error just in the debug screen..
earlier after control went to catch block.. i used to look the whole log, not the test case one but the main one..
as when we run test, one main overall log generates and one log for each test case

this is best now, easily can debug the issue

also do not write multiple catch block, just write one Excpetion e, it will itself tell like BadCredential when you hover on e
*/

//--======================================================
/*
 * -----------------how to find all debug points at one place-----------
 * in debug screen in right most corner on top.. one double arrow sign to expand.. here can find all breakpoint
 * select all and delete in once
 */

//--=======================================================
//----------no error in qny java file but still error mark on main project name
//first resolve dependency by force maven update on Project dropdown, 
//but still error, on running asks error exists do you want to run, yes run, it showed:
//compiled by latest java version error..
//actually in pom.xml, java version was 21, but I have java 17 on my system so issue occurred

//in tcs prject, we installed java 17, so same error, worked with java 11 only


//--============================================
//in exam error in pom.xml, needed to download dependecy separately.. there force maven project does not wprk
//or run install.sh, it installs all the dependency required if any dependecy error

//also i set working directpry inside project folder, so when trying to open, it was not opening the roject in ec;ipse
//so either, right click on project folder and open as eclipse project
//or set working directory as desktop only or whatever it gvives as default folder

//--=====================================================================
/*
GenerationType : IDENTITY, AUTO, SEQUENCE, TABLE, UUID added now
IDENTITY: incremented by DB for each table
SEQUENCE: maintains a sequence and updates id at overall all the tables
TABLE: maintains a separate table for sequence
UUID: creates uuid as pk
AUTO: Based on db and type of pk, automatically selects either identity, or sequence or uuid
*/

//--================================================================================================