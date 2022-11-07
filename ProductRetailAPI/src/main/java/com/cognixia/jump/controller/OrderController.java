package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
//import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.Payment;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "order", description = "the API for managing orders")
public class OrderController {

	@Autowired
	OrderRepository repo;
	
	@Autowired
	OrderService service;
	
	@Operation( summary = "Get all the books in the book table",
			description = "Gets all the books from the book table in the "
					+ "product_db database. Each book grabbed has an "
					+ "id, title, genre, pages, date, medium, qty, price, and author of the book.")
	@GetMapping("/order")
	public List<Order> getOrders(@CurrentSecurityContext(expression="authentication?.credentials") String username) 
			throws ResourceNotFoundException {
		return service.getOrders(username);
	}
	
	@ApiResponse(responseCode = "200", description = "Book has been found")
	@ApiResponse(responseCode = "404", description = "Book was not found")
	@GetMapping("/order/id")
	public ResponseEntity<?> getOrder(@RequestParam String id) throws ResourceNotFoundException {
		
		return service.getOrder(id);
	}
	
	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@CurrentSecurityContext(expression="authentication?.credentials") String username) 
			throws MethodArgumentNotValidException, ResourceNotFoundException {
		
		return service.createOrder(username);
	}

	@PutMapping("/order")
	public ResponseEntity<Payment> createPayment(@CurrentSecurityContext(expression="authentication?.credentials") String username, @Valid @RequestBody Payment payment) throws MethodArgumentNotValidException {
		
		return service.createPayment(username, payment);
	}
	
//	@DeleteMapping("/order")
//	public ResponseEntity<?> deleteOrder(@RequestParam String id) throws ResourceNotFoundException {
//		
//		return service.deleteOrder(id);
//	}
//	
//	@GetMapping("/order/book")
//	public List<Order> getOrderByBookid(String bookId) {
//		return repo.findByBookId(bookId);
//	}
//	
//	@GetMapping("/order/user")
//	public List<Order> getOrderByUserid(String userId) {
//		return repo.findByUserId(userId);
//	}
	
}
