package com.example.innovator24Dec.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Design {
	
	//Date myDate = new Date();
	//SimpleDateFormat dtformat = new SimpleDateFormat("dd/MM/yyyy"); //if write dd/mm/yyyy, it will not run, it must be MM
	//dtformat.format(myDate);//return type of this is formatted date
	//but myDate will still be same
	//ticketModel.setCreatedAt(dtformat.format(myDate));
	
	
	/*
	GenerationType : IDENTITY, AUTO, SEQUENCE, TABLE, UUID added now
	IDENTITY: incremented by DB for each table
	SEQUENCE: maintains a sequence and updates id at overall all the tables
	TABLE: maintains a separate table for sequence
	UUID: creates uuid as pk
	AUTO: Based on db and type of pk, automatically selects either identity, or sequence or uuid
	*/

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@JsonProperty(value = "designId", access = JsonProperty.Access.READ_ONLY)//can read get() but can not write set()
	//this is working perfectly as it ignores setter only not getter
	//if need getter then provide READ_ONLY
	//if need setter then provide WRITE_ONLY

	//@JsonIgnore
	//.andExpect(MockMvcResultMatchers.jsonPath("$[0].designId", Matchers.is(1))) this is failing if use 
	//@JsonIgnore as it will ignore both getter and setter, so can not find path error as it uses getter to get path
	private int designId; 
	//if keeping it as int then can not set id as null, which is must in new hibernate
	//but thing is primary id can not be null,, yeah pk will be updated by hibernate..
	//we just need null in json
	
	private String designName; //Casual Shoes, College Backpacks
	private String description;
	private String color;
	private int price;
	private String availabilityStatus; //In Stock, Out of Stock
	
	private String imageURL;
	//i mistakenly wrote imageUrl here but in test case it was imageURL.. so causing error Caused by: 
	//com.jayway.jsonpath.PathNotFoundException: No results for path: $[0]['imageURL']
	//not finding the path $[0]['imageURL'] as in Design class it was imageUrl and json path is taking names from Design class only
	//even after Refactor here, getter and setter names were not changed
	//when i refactored them manually then it started working as json path internally uses getter and setter
	
	
	private String availableSize; //S, M, L or One Size, if space mismatch then test cases fail
	private int designerId; // this we will set in api, we will get user details from security and then set user id
	//this way creating fk here
	
	
	//in above case in api code, set userId as DesignerId
	//but in below case, need to set complete userInfo as designer, that we get from security then fk created
	
	
	//Many designs by one designer
	//@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumn(name = "designerId_fk", referencedColumnName = "userId")
	//UserInfo userInfo1;
	
	//wherever we write joinColumn, fk is created there in this class table
	//fk is the pk of the othjer table that we are joining, like here we are joining user table
	//so referncedColumnName refers to pk of the other class that we are joining
	
	
	//Back Reference for above in UserInfo class
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "userInfo1")
	//@JsonIgnore
	//@JsonIgnoreProperties({"designId", "designName"}) //syntax given in description just hover on JsonIgnoreProperties
	//List<Design> designList;
	
	//wherever we write mappedBy, fk is not created there.. just for back refernce
	//in mappedBy we tell that which attribute in Design class is mapped to user class
	
	
	public Design() {
		super();
	}

	public Design(int designId, String designName, String description, String color, int price,
			String availabilityStatus, String imageURL, String availableSize, int designerId) {
		super();
		this.designId = designId;
		this.designName = designName;
		this.description = description;
		this.color = color;
		this.price = price;
		this.availabilityStatus = availabilityStatus;
		this.imageURL = imageURL;
		this.availableSize = availableSize;
		this.designerId = designerId;
	}
	
	
	public Design( String designName, String description, String color, int price,
			String availabilityStatus, String imageURL, String availableSize, int designerId) {
		super();
		this.designName = designName;
		this.description = description;
		this.color = color;
		this.price = price;
		this.availabilityStatus = availabilityStatus;
		this.imageURL = imageURL;
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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
