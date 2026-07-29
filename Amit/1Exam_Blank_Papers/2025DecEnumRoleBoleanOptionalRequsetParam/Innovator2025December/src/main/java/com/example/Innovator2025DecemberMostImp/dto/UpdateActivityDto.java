package com.example.Innovator2025DecemberMostImp.dto;

import com.example.Innovator2025DecemberMostImp.entity.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UpdateActivityDto {
	
	@Enumerated(EnumType.ORDINAL) //I added this
	private Status status;

	public UpdateActivityDto() {
		super();
	}

	public UpdateActivityDto(Status status) {
		super();
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
