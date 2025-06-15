package com.example.innovator24Dec.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Design {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int designId;
	
	private String designName; //Casual Shoes, College Backpacks
	private String description;
	private String color;
	private int price;
	private String availabilityStatus; //In Stock, Out of Stock
	private String imageUrl;
	private String availableSize; //S, M, L or One Size
	private int designerId;
	
	public Design() {
		super();
	}

	public Design(int designId, String designName, String description, String color, int price,
			String availabilityStatus, String imageUrl, String availableSize, int designerId) {
		super();
		this.designId = designId;
		this.designName = designName;
		this.description = description;
		this.color = color;
		this.price = price;
		this.availabilityStatus = availabilityStatus;
		this.imageUrl = imageUrl;
		this.availableSize = availableSize;
		this.designerId = designerId;
	}

	public int getDesignId() {
		return designId;
	}

	public void setDesignId(int designId) {
		this.designId = designId;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAvailableSize() {
		return availableSize;
	}

	public void setAvailableSize(String availableSize) {
		this.availableSize = availableSize;
	}

	public int getDesignerId() {
		return designerId;
	}

	public void setDesignerId(int designerId) {
		this.designerId = designerId;
	}
	
	
	
}
