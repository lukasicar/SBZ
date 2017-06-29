package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.ActionEvent;

public interface ActionEventDAO extends JpaRepository<ActionEvent, String>{

	ActionEvent findByCode(String code);
	
    List<ActionEvent> findAllByOrderByNameAsc();
	
}
