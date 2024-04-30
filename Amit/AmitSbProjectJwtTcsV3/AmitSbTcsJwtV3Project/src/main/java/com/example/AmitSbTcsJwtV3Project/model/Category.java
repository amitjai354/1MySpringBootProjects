package com.example.AmitSbTcsJwtV3Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore//because of this ignoring category id in json input and output both
    private Integer categoryId;

    @Column(unique = true)
    private String categoryName;

//    @OneToMany(mappedBy = "category")
//    private Set<Product> productSet;
    //this is a list , it will not be column in Category table, just for Bidirectional
    //give mapped by where you do not wa2nt to create fk and give join column where you want to give fk
    //this pruductSet is mapped by which column in Product class.. category
    //we give back reference so that when someone querying category, can get vdetails of products also inked to this category even
    //without having any column product in categviry
    //when selecting category, hiberbnate sees this mappedby and goes to category column in Product and internally makes
    // query thie to get oproducts linked to this cxategory
    //with bidirectional query we need not to write join query.. hibernate will do
    //if we do not write this mapped by here then when fecthing product we willnot get category details
    //mappedBy attributes we need not to take in input, automatically added.. will be shown in output byhibernate join
    //so better create new reqiuest class for entity, validation art eequest put here and db level put ibn entity class

    //we can put bulder code To() here in request class this method is neede as we need objects these class in service class


    public Category() {
    }

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

//    public Set<Product> getProductSet() {
//        return productSet;
//    }
//
//    public void setProductSet(Set<Product> productSet) {
//        this.productSet = productSet;
//    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
