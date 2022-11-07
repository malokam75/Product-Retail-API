package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	public Optional<User> findByUsername(String username);

	public void deleteByUsername(String username);

	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}
