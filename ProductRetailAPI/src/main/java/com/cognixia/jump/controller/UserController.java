package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.DuplicateResourceException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "user", description = "the API for managing users")
public class UserController {

	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Operation( summary = "Get all the users in the user table",
			description = "Gets all the users from the user table in the "
					+ "product_db database. Each user grabbed has an "
					+ "id, username, password, email, role, and enabled for activating account.")
	@GetMapping("/user")
	public List<User> getUsers() throws ResourceNotFoundException {
		
		if (repo.findAll() == null) {
			
			throw new ResourceNotFoundException("No user exists.");
		}return repo.findAll();
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) 
			throws DuplicateResourceException, MethodArgumentNotValidException {
		
		if ( repo.existsByUsername( user.getUsername() )) {
			throw new DuplicateResourceException("A user already exists with this username.");
			
		} else if ( repo.existsByEmail( user.getEmail() ) ) {
			throw new DuplicateResourceException("A user already exists with this email.");
		} else if(validUser(user)) {
			user.setId(null);
			user.setPassword(encoder.encode(user.getPassword()));
			User created = repo.save(user);
			return ResponseEntity.status(201).body(created);
		}
		return null;
	}
	
	public boolean validUser(User user) 
			throws DuplicateResourceException, MethodArgumentNotValidException {
		
		if (!user.getRole().startsWith("ROLE_") || !user.getRole().matches(user.getRole().toUpperCase())) {
			throw new DuplicateResourceException("This user's role is not properly assigned."
					+ "Must be in capital letters and begins with 'ROLE_'");
		}
		return true;
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user) 
			throws ResourceNotFoundException, DuplicateResourceException, 
			MethodArgumentNotValidException {
				
		if(repo.existsById(user.getId())){
			if (validUser(user)) {
				user.setPassword(encoder.encode(user.getPassword()));
				User updated = repo.save(user);
				return ResponseEntity.status(201).body(updated);
			}
			return null;
		}
		
		throw new ResourceNotFoundException("User", user.getId());	
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(@RequestParam String username) throws ResourceNotFoundException {
						
		Optional<User> found = repo.findByUsername(username);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException(username + " was not found");
		}
		repo.deleteByUsername(username);
		
		return ResponseEntity.status(201).body(found);
	}
	
}
