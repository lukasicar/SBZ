package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.ProductCategory;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, String>{

	
	ProductCategory findByCode(String code);
	
    List<ProductCategory> findAllByOrderByNameAsc();
	

}
