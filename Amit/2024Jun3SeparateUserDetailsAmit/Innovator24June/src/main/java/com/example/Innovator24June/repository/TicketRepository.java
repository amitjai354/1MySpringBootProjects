package com.example.Innovator24June.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovator24June.entity.TicketModel;
import java.util.List;




public interface TicketRepository extends JpaRepository<TicketModel, Integer>{
	
	List<TicketModel> findByCategory(String category);

}
