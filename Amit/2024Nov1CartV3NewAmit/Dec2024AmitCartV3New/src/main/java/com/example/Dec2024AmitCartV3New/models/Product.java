package com.example.Dec2024AmitCartV3New.models;

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
	
	@ManyToOne()
	@JoinColumn(name="seller_id", referencedColumnName = "userId", updatable = false)
	//can not update User from this class
	@JsonIgnore//do not want to shpw seller details while showing product details in flipkart
	private User seller;
	//this is uni directional relationship as in user class no attribute created for this
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	private Category category;
	//could have written OneToMany in Category class to maintain bidirectional relationship
	//mappedBy= category so this way Fk not created in Category table.. where use mapped by inside OneToMany
	//but maintaining uni directional relationship here
	//if writting mappedBy then no need of JoinColumn
	//mappedBy tells that we are maintaing bidirectional relationship with CartProduct class 
	//but FK will not be present in this table, mapped by mean birectional relationship

	public Product() {
		super();
	}

	public Product(Integer productId, String productName, Double price, User seller, Category category) {
		super();
		this.productId = productId;
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

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price + ", seller="
				+ seller + ", category=" + category + "]";
	}
	
}
