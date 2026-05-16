package com.example.Innovator2025June28.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonProperty("voterId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private UserInfo voter;

    @ManyToOne
    @JsonProperty("activityId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private SustainableActivity activity;

    
    //@CreationTimestamp //I added this
    private Instant timestamp; //what is this instant
    /*
     In Java, Instant represents a precise point in time (a timestamp) measured in UTC, independent of time zones. 
     It was introduced in Java 8 as part of the java.time package and is commonly used for logging, event tracking, 
     and time calculations.
     
     Instant.now()	Current timestamp from system clock	Instant current = Instant.now();
     
     Why Use Instant Instead of Date?
	Immutable & Thread-safe (unlike java.util.Date).
	Better precision (nanoseconds vs milliseconds).
	Part of modern Java Time API (introduced in Java 8).
	Works seamlessly with Duration, Period, ZonedDateTime, etc.
	
	Use @CreationTimestamp
Hibernate provides the @CreationTimestamp annotation for exactly this purpose. It tells Hibernate to automatically 
set the field to the current time when the entity is first persisted.

No need to set it manually in constructors or service code.
Hibernate will insert the current UTC timestamp when the row is created.
Since you’re using Instant, it will be stored as a precise point in time (UTC).

@CreationTimeStamp : Works only on INSERT (not updated later). If you also want an "updatedAt" field, use 
@UpdateTimestamp.
     */
    

    private boolean support;

    private String comment;

	public Vote() {
		super();
	}

	public Vote(Integer id, UserInfo voter, SustainableActivity activity, Instant timestamp, boolean support,
			String comment) {
		super();
		this.id = id;
		this.voter = voter;
		this.activity = activity;
		this.timestamp = timestamp;
		this.support = support;
		this.comment = comment;
	}
	
	public Vote(UserInfo voter, SustainableActivity activity,  boolean support,
			String comment) {
		super();
		
		this.voter = voter;
		this.activity = activity;
		
		this.support = support;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserInfo getVoter() {
		return voter;
	}

	public void setVoter(UserInfo voter) {
		this.voter = voter;
	}

	public SustainableActivity getActivity() {
		return activity;
	}

	public void setActivity(SustainableActivity activity) {
		this.activity = activity;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isSupport() {
		return support;
	}

	public void setSupport(boolean support) {
		this.support = support;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
    
	
}
