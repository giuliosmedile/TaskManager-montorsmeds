package com.uniroma3.montorsmeds.TaskManager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.uniroma3.montorsmeds.TaskManager.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	public Optional<User> findByUserName(String username);
	
}
