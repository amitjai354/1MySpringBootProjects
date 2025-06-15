package com.example.demo.amitSbProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id","product_id"}))
//this above code in tcs code.. why?
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cpid; //int vs integer.. if integer can be null

    private Integer quantity=1; //assigned in tcs code

    @ManyToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    @JsonIgnore // do not want to see this filed in json response that we return
    private Cart cart;
    //join column and mappedBy.. Fk created there where write jpoin column not there where mapped by
    //in insert, we are only passing  cartId and productId, quantity
    //this means.. we will write join coulmn here with name of new column.. cartId
    //cart id will be FK in this table

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;
    //Many cart products can be one product eg.. let say product is one.. galaxy ultra but can buy 2 or maore same
    //galaxy ultra so will add 2 or more ultra in the the cart..
    //Many cart product is mapped to one product
}

