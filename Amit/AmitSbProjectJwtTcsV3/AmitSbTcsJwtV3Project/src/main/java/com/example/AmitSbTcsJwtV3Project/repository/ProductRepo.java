package com.example.AmitSbTcsJwtV3Project.repository;

import com.example.AmitSbTcsJwtV3Project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(nativeQuery = true, value = "select p.category_id, p.price, p.product_id, p.seller_id, p.product_name from Product p, Category c where p.product_id = c.category_id and upper(p.product_name) like '%' || ?1 || '%' or upper(c.category_name) like '%' || ?2 || '%'")
    public List<Product> findByProductNameIgnoreCaseContainingOrCategoryCategoryNameIgnoreCaseContaining(String keyword, String keyword1);
    //Most imp if write OR will not work, keyword is case sensitive.. camel case Or
    //expects at least 2 arguments but only found 1; This leaves an operator of
    // type CONTAINING for property category.categoryName unbound
    //findByProductNameAndCategoryCategoryNameContaining
    //But currently querfy that is created has productName= and categoryName like so need to use Containing after product name aslo

    //findByProductNameContainingAndCategoryCategoryNameContaining if we are writting And means will fetch data if keyword in both product and category name
    //only then otherwise if present in anyone then will not fetch

    //findByProductNameContainingOrCategoryCategoryNameContaining got the result but when using like always
    // convert to upper or lower case

    //if you are writing your query you must fetch all columns otherwise error..* also does not work so write all cloumn names

    List<Product> findBySellerUserId(Integer sellerId);
    Optional<Product> findBySellerUserIdAndProductId(Integer sellerId, Integer productId);
}
