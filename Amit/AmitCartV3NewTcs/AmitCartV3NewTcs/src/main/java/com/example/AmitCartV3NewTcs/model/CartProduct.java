package com.example.AmitCartV3NewTcs.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cpId;
	
	private Integer quantity;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="product_id", referencedColumnName="productId")
	private Product product;
	//one product eg s24 can be added in many carts as cartProduct
	
	@ManyToOne()
	@JoinColumn(name="cart_id", referencedColumnName = "cartId")
	private Cart cart;
}
