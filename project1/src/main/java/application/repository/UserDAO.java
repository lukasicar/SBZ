package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.User;

public interface UserDAO extends JpaRepository<User, String>{

	<E extends User> E findByUsername(String username);

	
}
