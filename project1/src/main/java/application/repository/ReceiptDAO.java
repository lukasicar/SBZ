package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Receipt;

public interface ReceiptDAO extends JpaRepository<Receipt, Integer>{

	List<Receipt> findAllByOrderByCodeAsc();
	
}
