package com.example.demo.amitSbProject.models;

//import jakarta.persistence.Entity; for spring boot version 3 and above
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @ManyToOne()//can write cascadeAll if we want.. not mandatory
    @JoinColumn(name="category_id", referencedColumnName = "categoryId")
    private Category category;
    //here in product class primary key is product id and fk is categoryId.. so we need to write joinCloumn here
    //referenced column will be the pk in other table which we are refering.. categoryId in Category class

    @ManyToOne()
    @JoinColumn(name="seller_id", referencedColumnName = "userId", updatable = false)
    @JsonIgnore
    private MyUser seller;
    //updatable = false, we do that when responsibilty of creating/updating the referenced column's  entity
    // is not the current entity but in another entity
    //means Product will not update the MyUser entity


}
