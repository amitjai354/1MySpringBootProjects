package com.example.demo.amitSbProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer cartId;

    private double totalAmount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
    private List<CartProduct> cartProducts;
    //join column and mappedBy.. join we write there where we want to create FK, if mapped by the no FK created in this class

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private MyUser myUser;
    //user_id is created as FK column in Cart table and this is reffering to pk userId in MyUser table
    //name is my_user_user_id if do not give joincolumn.. automatically added by hibernate
    //since userid is only pk in myuser class so no need to give referenced column name as userid
    //cascadetype.remove: if delete userid from Myuser table then delete this row from this cart table also
    // if deleting parent then delete from child also.. if deleting myuser from cart then delete cart also
    //if saving my user need not save cart.. if saving cart then save user if not exist

    //Let two tables APk and BFk and B has FK as pk of A.. Table having FK is child and other one is parent
    //case1: insert: we can not insert in B if there is no existing PK in A , first add in A then in B only
    //if inserting in B then insert in A first, if already existing in A then no problem
    //case2: delete: here reverse, can delete from B but not from A as there may be some child in B,
    //if deleting from A then delete from B also
    //case3: can not update in both A and B.. if updating then update in both

    //in tcs, not used @joincolumn so column is user_user_id as class is user
}
