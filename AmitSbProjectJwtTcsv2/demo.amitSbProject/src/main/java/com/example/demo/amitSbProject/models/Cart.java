package com.example.demo.amitSbProject.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId; //integer can be null in db

    private double totalAmount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart") //this cartProduct attrbt here is mapped by
    // attribut cart in the class CartProduct
    private List<CartProduct> cartProducts; //this must be list otherwise error.. for back reference onlt
    //join column and mappedBy.. which one to write where..FK here also there also
    //in insert, we are only passing  totalAmount and UserId

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private MyUser myUser;//name is my_user_user_id if do not give joincolumn.. automatically added by hibernate
    //@JoinColumn(name = "myuser", referencedColumnName = "userId")//not needed as in the final output, if  there is
    //no such column user in the cart table.. but we have userid currently..
    //if we do not add thnen  y defualt added  by hibernate as my_user_user_id
    //if we add we can give column name as per our wish..
    //since userid is only pk in myuser class so no need to give referenced column name as userid
    //cascadetype.remove: deletion of an entity and its child entity

    //in tcs, not used @joincolumn so column is user_user_id as class is user
    //complete notes for all these
}
