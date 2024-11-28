package com.example.Innovator24June.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private int ticketId;
	private String issue;
	private String category;
	private String createdAt;
	private boolean isPriority = true;
	private String comments;
	private String status = "New";
	private int clientId;
	//these many fields given in question paper
	
	public TicketModel() {
		super();
	}
	
	public TicketModel(int ticketId, String issue, String category, String comments) {
		super();
		this.ticketId = ticketId;
		this.issue = issue;
		this.category = category;
		this.comments = comments;
	}

	public TicketModel(boolean isPriority, String status) {
		super();
		this.isPriority = isPriority;
		this.status = status;
	}

	public TicketModel(int id, int ticketId, String issue, String category, String createdAt, boolean isPriority,
			String comments, String status, int clientId) {
		super();
		this.id = id;
		this.ticketId = ticketId;
		this.issue = issue;
		this.category = category;
		this.createdAt = createdAt;
		this.isPriority = isPriority;
		this.comments = comments;
		this.status = status;
		this.clientId = clientId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isPriority() {
		return isPriority;
	}

	public void setPriority(boolean isPriority) {
		this.isPriority = isPriority;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
	
	
}
