package com.example.demo.amitSbProject.repositories;

import com.example.demo.amitSbProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//not mandatory.. already done in the implementation class
public interface ProductRepo extends JpaRepository<Product, Integer> {
   List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName, String categoryName);
   //in interface, we do not define methods.. and by default all are public
   //if we give name as per jpa convention, need not to write queries..
   //we write findBy then any attribute from Product class on basis of which we are trying to search eg productName
   //in Product we have one attribue Category.. so we can use category class attributes as well eg categoryName

   //findByProductNameOrCategoryName:- Failed to create query for method : No property 'name' found for
   // type 'Category'; Traversed path: Product.category

   //findByProductNameOrCategoryCategoryName :- Failed to create query for method :expects at least 2 arguments but only found 1.
   // This leaves an operator of type SIMPLE_PROPERTY for property category.categoryName unbound.
   //i guessed write.. we wi;ll first write referenced class name then write its attribute.. CategoryCategoryName
   //but we need to provide 2 argumnets here.. one for pructname and one for category name

   //we have to return the product containg the keyword.. product name is apple.. tablet.. keyword is Tablet
   //Containing  :/findByFirstnameContaining : .. where x.firstname like ?1 (parameter bound wrapped in %)
   //IgnoreCase  :findByFirstnameIgnoreCase :  .. where UPPER(x.firstname) = UPPER(?1)

   /* //below query working in h2 db
select * from product where upper(product_name) like upper('%tablet%');

select
   product0_.product_id as product_1_4_,
   product0_.category_id as category4_4_,
   product0_.price as price2_4_,
   product0_.product_name as product_3_4_,
   product0_.seller_id as seller_i5_4_
           from
   product product0_
   left outer join
   category category1_
   on product0_.category_id=category1_.category_id
           where
   upper(product0_.product_name) like upper('%tablet%')
   or upper(category1_.category_name) like upper('%tablet%');
*/

   //select * from product where product_name like  '%tablet%';  givres result
   //if use "%tablet%" .. ERROR: coulmn %tablet% not found..
   // "" means column and '' means string in sql

   //UPPER(text) is sql fn to convert text in upper case


   /*
   3. LIKE Query Methods
For many simple LIKE query scenarios, we can take advantage of a variety of keywords to create query
methods in our repositories.


3.1. Containing, Contains, IsContaining and Like
Let’s look at how we can perform the following LIKE query with a query method:
SELECT * FROM movie WHERE title LIKE '%in%';

First, let’s define query methods using Containing, Contains and IsContaining:
List<Movie> findByTitleContaining(String title);
List<Movie> findByTitleContains(String title);
List<Movie> findByTitleIsContaining(String title);

List<Movie> results = movieRepository.findByTitleContaining("in");
results = movieRepository.findByTitleIsContaining("in");
results = movieRepository.findByTitleContains("in");


Spring also provides us with a Like keyword, but it behaves slightly differently in that we’re required to
provide the wildcard character with our search parameter.
Let’s define a LIKE query method:
List<Movie> findByTitleLike(String title);
Now we’ll call our findByTitleLike method with the same value we used before but including the wildcard characters:
results = movieRepository.findByTitleLike("%in%");
    */

   List<Product> findByProductNameContaining(String keyword);
   //Containing: select ... like %:username%
   //
   // List<User> findByUsernameContaining(String username);


}
