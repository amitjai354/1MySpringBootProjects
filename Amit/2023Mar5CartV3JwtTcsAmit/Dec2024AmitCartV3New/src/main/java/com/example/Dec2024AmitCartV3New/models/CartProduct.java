package com.example.Dec2024AmitCartV3New.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "cart_id", "product_id"}))
@Entity
public class CartProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cpId;
	
	//in paper, cart given in cartProdct and cartProduct given in cart , 
	//so where to create fk and where to write mapped by..
	//here cart to cartProduct is 1 to Many so if create fk in cart, fk will be list of cartProducts ids
	//that is not good but if create fk here in cartProduct then, fk is single cartProduct Id
	@ManyToOne()
	@JoinColumn(name = "cart_id", referencedColumnName = "cartId")
	@JsonIgnore
	private Cart cart;//cartId is inside this Cart class
	
	//in exam paper data loader given, productId inside cartProduct but not given cartProduct in Product
	//so adding fk here in cartProduct
	//one product can be added to many cart products, as many user may buy same product
	@ManyToOne()
	@JoinColumn(name = "product_id", referencedColumnName = "productId")
	private Product product;
	
	private Integer quantity = 1;

	public CartProduct() {
		super();
	}

	public CartProduct(Cart cart, Product product, Integer quantity) {
		super();
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

	@Override
	public String toString() {
		return "CartProduct [cpId=" + cpId + ", cart=" + cart + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
}
