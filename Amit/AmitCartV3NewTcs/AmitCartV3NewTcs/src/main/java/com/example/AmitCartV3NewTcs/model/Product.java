package com.example.AmitCartV3NewTcs.model;

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
    private UserModel seller;
    
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
