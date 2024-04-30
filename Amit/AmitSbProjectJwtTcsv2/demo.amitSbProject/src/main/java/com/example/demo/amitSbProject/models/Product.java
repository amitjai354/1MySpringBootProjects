package com.example.demo.amitSbProject.models;

//import jakarta.persistence.Entity; for spring boot version 3 and above
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer productId;//integer can be null in db
    private String productName;

    private double price;

    @ManyToOne()
    //if write cascadeType.All here means when saving product save category also and if wrote in category means when saving
    //category save product also but since we have not written product in catehory so this unidirectional means when fetching
    //product we will get category but when fetching category we will not get product

    // can write cascadeAll if we want.. not mandatory
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    //if write cascadeAll then before saving to product, will save data in category
    //but since category Electronics is already defined why do we create need category again.. unique so error
    @JoinColumn(name="category_id", referencedColumnName = "categoryId" )
    private Category category;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="seller_id", referencedColumnName = "userId", updatable = false)
    @JsonIgnore
    private MyUser seller;
    //updatable = false, we do that when responsibilty of creating/updating the referenced column's  entity
    // is not the current entity but in another entity
    //means Product will not update the MyUser entity
    //we need not pass seller details in json when creating product as we will not create seller


}
