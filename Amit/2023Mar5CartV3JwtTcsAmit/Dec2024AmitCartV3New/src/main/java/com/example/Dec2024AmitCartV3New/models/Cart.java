package com.example.Dec2024AmitCartV3New.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	
	private Double totalAmount;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private User user;
	
	//if try to create fk here then fk will be list cartProduct ids, so better create fk in cartProduct
	//as there fk is single unique cart id no list there
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
	private List<CartProduct> cartProducts;//cart is inside this cartProduct
	
	public void updateTotalAmount(Double price) {
		this.totalAmount += price;
	}

	public Cart() {
		super();
	}

	public Cart(Integer cartId, Double totalAmount, User user, List<CartProduct> cartProducts) {
		super();
		this.cartId = cartId;
		this.totalAmount = totalAmount;
		this.user = user;
		this.cartProducts = cartProducts;
	}
	
	//below one I have created to use in DataLoader as in this exam did not use DataLoader so not given other
	//constructors, but in exam where using DataLoader, they have given multiple constructors.. without PK as well
	public Cart(Double totalAmount, User user) {
		super();
		this.totalAmount = totalAmount;
		this.user = user;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", totalAmount=" + totalAmount + ", user=" + user + ", cartProducts="
				+ cartProducts + "]";
	}
	
}
