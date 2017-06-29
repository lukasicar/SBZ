package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Buyer;

public interface BuyerDAO extends JpaRepository<Buyer, String>{

	Buyer findByUsername(String username);
	
}
