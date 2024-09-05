package entity;

import jakarta.persistence.Entity;

@Entity
public class TicketModel {

	private String createdAt;
	private boolean isPriority = true;
	private String comments;
	private String status = "New";
	private int clientId;
}
