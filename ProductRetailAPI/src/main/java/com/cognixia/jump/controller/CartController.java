package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
//import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cart;
import com.cognixia.jump.repository.CartRepository;
import com.cognixia.jump.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "cart", description = "the API for managing what items are in the cart")
public class CartController {

	@Autowired
	CartRepository repo;
	
	@Autowired
	CartService service;
	
	@Operation( summary = "Get all the books in the book table",
			description = "Gets all the books from the book table in the "
					+ "product_db database. Each book grabbed has an "
					+ "id, title, genre, pages, date, medium, qty, price, and author of the book.")
	@GetMapping("/cart")
	public List<Cart> getCart(@CurrentSecurityContext(expression="authentication?.credentials") String username) throws ResourceNotFoundException {
		return service.getCart(username);
	}
	
	@GetMapping("/cart/count")
	public int getBookCountInCart(@CurrentSecurityContext(expression="authentication?.credentials") String username) {
		return service.getBookCountInCart(username, null);
	}
	

	@PostMapping("/cart")
	public ResponseEntity<Cart> createCart(@Valid @RequestParam String bookId, 
			@CurrentSecurityContext(expression="authentication?.credentials") String username) 
					throws MethodArgumentNotValidException, ResourceNotFoundException {
		
		return service.createCart(bookId, username);
	}

	@ApiResponse(responseCode = "201", description = "Book has been added sucessfully")
	@ApiResponse(responseCode = "404", description = "Book was not found")
	@PutMapping("/cart")
	public ResponseEntity<?> updateCart(@Valid @RequestParam int qty, @RequestParam String bookId, 
			@CurrentSecurityContext(expression="authentication?.credentials") String username) 
					throws ResourceNotFoundException, MethodArgumentNotValidException {
		
		return service.updateQtyInCart(qty, bookId, username);
	}
	
	@DeleteMapping("/cart")
	public ResponseEntity<?> deleteCart(@RequestParam String bookId,
			@CurrentSecurityContext(expression="authentication?.credentials") String username)
					throws ResourceNotFoundException {
		
		return service.deleteCart(bookId, username);
	}
	
}
