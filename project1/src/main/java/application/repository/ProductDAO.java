package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Product;

public interface ProductDAO extends JpaRepository<Product, String>{


	Product findByCode(String code);
	
    List<Product> findAllByOrderByNameAsc();
	
}

