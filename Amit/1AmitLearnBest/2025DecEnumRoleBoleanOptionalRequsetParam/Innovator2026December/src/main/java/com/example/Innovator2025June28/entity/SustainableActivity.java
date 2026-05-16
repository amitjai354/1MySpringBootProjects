package com.example.Innovator2025June28.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SustainableActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String description;
	
	@Enumerated(EnumType.ORDINAL) //I added this
	private Category category;
	
	//@ManyToOne     
	//@JoinColumn(name="user_id", referencedColumnName = "userId") 
	//@JsonIgnore
	//@JsonIgnoreProperties({"email", "password"})
	//this was not given in the exam, i did not write as well
	//can not create fk in user info as there fk will be a list
	//where we write join column fk is created in that table and fk is pk of other table
	
	@ManyToOne  // many activities can be posted by one user.
	@JsonProperty("postedById")  //Jackson annotation. Renames the JSON field to postedById instead of postedBy
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")//Jackson annotation. Prevents infinite recursion in bidirectional relationships by using an identifier (here, the id property of UserInfo) instead of serializing the full object.
	@JsonIdentityReference(alwaysAsId = true)
	private UserInfo postedBy;
	
	//int postedBy;  earlier they used to give int
	
	
	
	
	
	
	
/*
	//@ManyToOne // many activities can be posted by one user.
	//@JsonProperty("postedById")  //Jackson annotation. Renames the JSON field to postedById instead of postedBy
	
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	//Jackson annotation. Prevents infinite recursion in bidirectional relationships by using an identifier 
	//(here, the id property of UserInfo) instead of serializing the full object.
	
	//@JsonIdentityReference(alwaysAsId = true) 
	// Ensures that when serializing, Jackson outputs only the ID (e.g., postedById: 1) instead of embedding the entire 
	//UserInfo object.
	 
	//private UserInfo postedBy;
	
	//Result: In JSON, you’ll see just the user’s ID, not the full user object.
	 

@JsonIgnore → field is completely hidden from JSON.
@JsonIgnoreProperties --> few attributes of the object are hidden
@JsonIdentityInfo + @JsonIdentityReference(alwaysAsId = true) → field is shown as just the ID, not the full object.
	
-------------------------------	
with exam code :  "postedById": 1  given in the exam response so this is the one to use
	{
  "id": 1,
  "title": "Community Tree Planting",
  "description": "An event where local community members come together to plant trees in urban areas.",
  "category": "GREEN_SPACE",
  "postDate": "2025-05-10",
  "status": "CLOSED",
  "estimatedCost": 5000,
  "isVerified": true,
  "location": "Central Park, New York",
  "postedById": 1
}

-------------
with my code if use @JsonIgnore :
{
  "id": 1,
  "title": "Community Tree Planting",
  "description": "An event where local community members come together to plant trees in urban areas.",
  "category": "GREEN_SPACE",
  "postDate": "2025-05-10",
  "status": "CLOSED",
  "estimatedCost": 5000,
  "isVerified": true,
  "location": "Central Park, New York"
}

--------------
//if use @JsonIgnoreProperties({"email", "password"})
	 
	 {
  "id": 1,
  "title": "Community Tree Planting",
  "description": "An event where local community members come together to plant trees in urban areas.",
  "category": "GREEN_SPACE",
  "postDate": "2025-05-10",
  "status": "CLOSED",
  "estimatedCost": 5000,
  "isVerified": true,
  "location": "Central Park, New York",
  "postedBy": {
  		"userId": 1,
  		"name": "Ankit"
		}
	}
		
Compare behaviors:
@JsonIgnore → hides the entire field (postedBy won’t appear at all in JSON).

@JsonIgnoreProperties → shows the related object, but skips specific fields inside it.

@JsonIdentityInfo + @JsonIdentityReference(alwaysAsId = true) → shows only the ID (postedById: 1), not the full object.

Which to use?
If you want no postedBy at all → @JsonIgnore.

If you want a trimmed-down postedBy object → @JsonIgnoreProperties.

If you want just the ID (postedById) → @JsonIdentityInfo + @JsonIdentityReference.

"postedById": 1  given in the exam
------------------------------------------

//int postedBy;  
earlier they used to give int, 

//but now they are giving: 
 UserInfo postedBy;
 
 and they need output like this: "postedById": 1
 //if we use jsonColumn and json ignore properties it will come like this: 
 "postedBy": {
  		"userId": 1
		}
-----------------------------------------------------------------------------------------------------------------
	 */
	
	
	
	
	
	
	
	
//-----------------------------------------------------------------------	
	//@CreationTimestamp //I added this
	private LocalDate postDate;
	
/*
Great question! Since postDate is not being passed in the request body, you have two main options for ensuring 
it gets automatically set when the entity is created:

1. Use @CreationTimestamp (Hibernate)
If you want Hibernate to automatically populate the date when the entity is first persisted:

@CreationTimestamp
private LocalDate postDate;
This will insert the current date into the column when the record is created.

You don’t need to pass it in the request; Hibernate will handle it.
Works well if you only need the creation date and don’t plan to update it later.


2. Set it Manually in Service Layer
If you prefer more control, you can set it in your service when saving:

activity.setPostDate(LocalDate.now());
This way, you decide when and how the date is assigned (e.g., only on creation, or also on updates).

Which one to choose?
If postDate should always represent the creation date → use @CreationTimestamp.
If you want flexibility (e.g., sometimes override the date) → set it manually in your service.

Since your exam snippet shows "postedDate":"2025-05-10" as a created date, the cleanest approach is to 
annotate with @CreationTimestamp. That way, it will automatically be added without requiring input.

---------
if i do not do anything of the above, will it automatically add post date:

If you don’t explicitly do anything (like @CreationTimestamp or setting it in your service), then 
postDate will remain null when the entity is saved. JPA/Hibernate does not automatically populate LocalDate fields 
unless you tell it to.


------------------------
CreationTimestamp will add second also or it will just add the date :\

@CreationTimestamp works with Hibernate and is designed to automatically populate a field with the current timestamp 
when the entity is first persisted.

Here’s the key detail:
If you annotate a field of type LocalDateTime → you’ll get date + time (including seconds, milliseconds).

If you annotate a field of type LocalDate → Hibernate will only store the date part (year, month, day). 
The time portion is discarded because LocalDate doesn’t support hours/minutes/seconds.

-------------------------------------------------------------------

@CreationTimeStamp : Works only on INSERT (not updated later). If you also want an "updatedAt" field, use 
@UpdateTimestamp.

-----------------------------------------------------------------------
*/
	
	
	
	
	@Enumerated(EnumType.ORDINAL) //I added this
	private Status status;
	
	
	
	private int estimatedCost;
	
	private boolean isVerified;
	
	private String location;

	public SustainableActivity() {
		super();
	}

	public SustainableActivity(int id, String title, String description, Category category, UserInfo postedBy,
			LocalDate postDate, Status status, int estimatedCost, boolean isVeroified, String location) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.postedBy = postedBy;
		this.postDate = postDate;
		this.status = status;
		this.estimatedCost = estimatedCost;
		this.isVerified = isVeroified;
		this.location = location;
	}
	
	public SustainableActivity(String title, String description, Category category, UserInfo postedBy,
			 Status status, int estimatedCost, boolean isVeroified, String location) {
		super();
		
		this.title = title;
		this.description = description;
		this.category = category;
		this.postedBy = postedBy;
		
		this.status = status;
		this.estimatedCost = estimatedCost;
		this.isVerified = isVeroified;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public UserInfo getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(UserInfo postedBy) {
		this.postedBy = postedBy;
	}

	public LocalDate getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDate postDate) {
		this.postDate = postDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(int estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public boolean isVeroified() {
		return isVerified;
	}

	public void setVeroified(boolean isVeroified) {
		this.isVerified = isVeroified;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	
}
