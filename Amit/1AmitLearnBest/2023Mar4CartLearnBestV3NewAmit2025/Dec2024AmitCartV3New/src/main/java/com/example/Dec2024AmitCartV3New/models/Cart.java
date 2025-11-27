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
	
	//json ignore means if showing cart details, do not show its user details
	//one user will have one cart only, either user have cart id or cart has user id, generally in user table we do not put extra details other than user details
	//here join column is not written, also mapped by is not written.. so this is join column only so fk present here only
	//actually if there is only one attribute as pk in user class then no need to write join column here, automatically done by spring boot
	//userId is the fk in this cart table, not the complete user class, as if join column present there we mention user_id, referneced column name= userId
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	//@JoinColumn(name="user_id", referencedColumnName="userId") //not given in the exam paper, added by default by springboot id only attribute is pk in user table
	@JsonIgnore
	private User user;
	//if we do not give Join Column and mapped By then by default join column is created with column name as 
	//we user _ for camel case eg userId = user_id
	//if we have only one column as PK so no neeed to give join column as by default FK will refer to this PK only
	//this is uni directional relationship as in user class no attribute created for this
	//cascade Remove given in exam.. whatever remove here remove from this mentioned class as well
	//mean if removing anything from cart then remove the user from User who added this here.. delete user
	//where this is written, that is parent.. means delete operation will cascade from Parent to child entity
	
	//Generally this is written in User class.. so that when delete any customer.. dlete its cart also
	//but here we are maintaining uni directional relationship.. so may be that is why writting here..
	//but if bidirectional relation then if write Cascade Remove in user class then it means user class is parent
	//and this class is child class.. so if delete customer then delete its cart as well
	
	//only this cascade is written in complete exam..
	
	
	//one cart may have multiple cart products
	//if try to create fk here then fk will be list of cartProduct ids, so better create fk in cartProduct
	//as there fk is single unique cart id no list there
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
	private List<CartProduct> cartProducts;//cart is inside this cartProduct
	//if writting mappedBy then no need of JoinColumn
	//mappedBy tells that we are maintaing bidirectional relationship with CartProduct class 
	//but FK will not be present in this table, mapped by mean birectional relationship
	//if mapped by written then fk is not in this table
	//if join column written then fk is present in that table, sometimes when we have only pk in the table, join column is not written there
	//spring boot automatically adds that.. but mapped by will also be not written there, so assume this is join column only
	//like below present in cartProduct class
	//@ManyToOne()
	//@JoinColumn(name = "cart_id", referencedColumnName = "cartId")
	//@JsonIgnore
	//private Cart cart;//cartId is inside this Cart class
	//so cart table does not have any column cartProduct but cart product class has a fk column cartId
	//also attribute is written for class only like private Cart cart; but in table, only cart id goes.. 
	//as mentioned inside.. @JoinColumn(name = "cart_id", referencedColumnName = "cartId") so cart_id present in the table not the cart
	
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
