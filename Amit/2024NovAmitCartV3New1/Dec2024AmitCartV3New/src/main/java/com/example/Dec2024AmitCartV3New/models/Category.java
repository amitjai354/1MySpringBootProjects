package com.example.Dec2024AmitCartV3New.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer categoryId;
	//json ignore on primary key, so can not use in json request and response
	
	@Column(unique = true)
	private String categoryName;
	
//	I am writting this below code.. not given in exam
//	@OneToMany(fetch=FetchType.EAGER, mappedBy = "category")
//	List<Product> productList;
//	//attribute name in Product class that is maintaining this bi directional relationship
	//if writting mappedBy then no need of JoinColumn
	//mappedBy tells that we are maintaing bidirectional relationship with CartProduct class 
	//but FK will not be present in this table, mapped by mean birectional relationship

	public Category() {
		super();
	}

	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}
	
}
