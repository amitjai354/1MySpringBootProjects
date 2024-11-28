package com.example.Innovator24June.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovator24June.entity.TicketModel;



public interface TicketRepository extends JpaRepository<TicketModel, Integer>{
	
	List<TicketModel> findByCategory(String category);

}
