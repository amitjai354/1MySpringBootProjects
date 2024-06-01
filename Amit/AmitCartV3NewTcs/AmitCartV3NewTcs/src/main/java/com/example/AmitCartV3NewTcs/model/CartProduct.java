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
	
	//@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL) 
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE) 
	//if writing cascade then in data loader giving error
	//@ManyToOne()
	@JoinColumn(name="product_id", referencedColumnName="productId")
	private Product product;
	//one product eg s24 can be added in many carts as cartProduct
	
	@ManyToOne()
	@JoinColumn(name="cart_id", referencedColumnName = "cartId")
	private Cart cart;
	
	public CartProduct() {
		super();
	}

	public CartProduct(Integer cpId, Integer quantity, Product product, Cart cart) {
		super();
		this.cpId = cpId;
		this.quantity = quantity;
		this.product = product;
		this.cart = cart;
	}
	

	public CartProduct(Integer quantity, Product product, Cart cart) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.cart = cart;
	}

	public Integer getCpId() {
		return cpId;
	}

	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	
}
