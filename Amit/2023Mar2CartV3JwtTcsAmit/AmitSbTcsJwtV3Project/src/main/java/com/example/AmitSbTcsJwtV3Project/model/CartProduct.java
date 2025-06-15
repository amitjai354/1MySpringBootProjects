package com.example.AmitSbTcsJwtV3Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Entity
public class CartProduct {
    //can give multiple unique constraints in one go using @Table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cpId;

    @ManyToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    @JsonIgnore
    private Cart cart;
    //java camelcase names are replaced by underscore in betweens
    //here referenced cplumn name is written wrt to java in table cart this is cart_id
    //referenced column name is the pk of the other (cart) table that will be fk here
    //in java we are creating attribute of complete cart class for back reference
    //but in table only cart_id column will be created as fk

    //since cartId is only pk in cart so no need to give this referenced column name here
    //wwe do not need fetch type here as when fetching cart product, need not cart details
    //cascade also not required here as when doing anything in cart product.. need not to do same in cart

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    private Integer quantity = 1;

    public CartProduct() {
    }

    public CartProduct(Integer cpId, Cart cart, Product product, Integer quantity) {
        this.cpId = cpId;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
