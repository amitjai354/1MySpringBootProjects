package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.TicketModel;

public interface TicketRepository extends JpaRepository<TicketModel, Integer>{

}
