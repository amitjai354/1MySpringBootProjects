package com.example.AmitCartV3NewTcs.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	private Double totalAmount;
	
	@OneToMany(mappedBy = "cart")
	@JsonIgnore
	//@JoinColumn(name="cp_id", referencedColumnName="cpId")
	//writting mapped so not write @JoinColumn as we do not need FK here in cart, here FK will be list
	//as for one cart id there can be multiple cart products so cart products id will be list here
	//also OneToMany means Cart product will be list
	private Set<CartProduct> cartProduct;
	//Biderectional when getting cart will get cart products details as well
	//Very very imp:
	//in Bidirectional, always ignore any one side otherwise infinite records.. because
	//when gettong carts, It will fetch cartProducts also
	//but when fetching cartProducts then there again we have cart so will fetch cart again.. so on
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name="user_id", referencedColumnName="userId", updatable=false)
	private UserModel user;
	//Users_User_Id will be column name if do not write user_id here in name
	
	public Cart() {
		super();
	}
	public Cart(Integer cartId, Double totalAmount, Set<CartProduct> cartProduct, UserModel user) {
		super();
		this.cartId = cartId;
		this.totalAmount = totalAmount;
		this.cartProduct = cartProduct;
		this.user = user;
	}
	
	public Cart(Double totalAmount, Set<CartProduct> cartProduct, UserModel user) {
		super();
		this.totalAmount = totalAmount;
		this.cartProduct = cartProduct;
		this.user = user;
	}
	
	public Cart(Double totalAmount, UserModel user) {
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
	public Set<CartProduct> getCartProduct() {
		return cartProduct;
	}
	public void setCartProduct(Set<CartProduct> cartProduct) {
		this.cartProduct = cartProduct;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	
}
