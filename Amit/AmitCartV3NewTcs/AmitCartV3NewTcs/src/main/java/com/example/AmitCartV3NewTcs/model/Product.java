package com.example.AmitCartV3NewTcs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
    private String productName;
    private Double price;
    
    @ManyToOne
    @JoinColumn(name="seller_id", referencedColumnName="userId")
    @JsonIgnore//in response getting complete user details with product so ignore this
    private UserModel seller;
    
    //Sringboot returns null for relational(FK) entity after saving
	//in ManyToOne missed cascadeType.Persist
	//Persist, Merge, Remove, Refresh, Detach
	//Remove means if removing here, remove in other class also 
	//eg if removing product, remove seller user from db also, reverse is good that if remove user, 
	//then remove products added by user seller
	//we should not write cascadeAll on ManyToOne side instead, write on OneToMany side
	//as Many products related to one user, if delete any product then delete user also then means
	//delete all products, reverse is correct if remove user, remove all products

    
    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "categoryId")
    private Category category;

	public Product() {
		super();
	}

	public Product(Integer productId, String productName, Double price, UserModel seller, Category category) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.seller = seller;
		this.category = category;
	}
	
	

	public Product(String productName, Double price, UserModel seller, Category category) {
		super();
		this.productName = productName;
		this.price = price;
		this.seller = seller;
		this.category = category;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public UserModel getSeller() {
		return seller;
	}

	public void setSeller(UserModel seller) {
		this.seller = seller;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
