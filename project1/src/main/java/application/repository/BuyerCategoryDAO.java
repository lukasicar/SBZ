package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.BuyerCategory;

public interface BuyerCategoryDAO extends JpaRepository<BuyerCategory, String>{

	BuyerCategory findByCode(String code);
	
    List<BuyerCategory> findAllByOrderByNameAsc();
}
