package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Cart;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.Payment;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.CartRepository;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	CartRepository repoC;
	
	@Autowired
	UserRepository repoU;
	
	@Autowired
	BookRepository repoB;

	public List<Order> getOrders(String username) throws ResourceNotFoundException {
		User user = repoU.findByUsername(username).get();

		
		if (repo.findByUserId(user.getId()) == null) {
		
			throw new ResourceNotFoundException(username + " has not made an order yet.");
		}
		return repo.findByUserId(user.getId());
		
	}

	public ResponseEntity<?> getOrder(String id) throws ResourceNotFoundException {
		
		Optional<Order> found = repo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Order", id);
		}
		
		return ResponseEntity.status(201).body(found);
	}

	public ResponseEntity<Order> createOrder(String username) 
			throws MethodArgumentNotValidException, ResourceNotFoundException {
		
		User user = repoU.findByUsername(username).get();
		List<Cart> cart = repoC.findByUserId(user.getId());
		
		double price = 0;
		
		if (!cart.isEmpty()) {
			
			for (int i = 0; i < cart.size(); i++) {
				price += cart.get(i).getPrice()*cart.get(i).getQty();
			}
			Order newOrder = new Order();
			newOrder.setId(null); 
			newOrder.setUserId(user.getId());
			newOrder.setUser(username);
			newOrder.setCart(cart); 
			newOrder.setTotPrice(price); 
			if (user.getPayment()==null) {
				throw new ResourceNotFoundException(username + " does not have a payment method. "
						+ "Please add a new payment method before completing order.");
			} else {
				newOrder.setPayment(user.getPayment());
			}
			Order created = repo.save(newOrder);
			
			int i = 0;
			Book book = new Book();
			while (i < cart.size()) {
				book = repoB.findById(cart.get(i).getBookId()).get();
				book.setQty(book.getQty() - cart.get(0).getQty());
				repoB.save(book);
				i++;
			}
			return ResponseEntity.status(201).body(created);
		}
		
		throw new ResourceNotFoundException("Cannot find cart for user:" + user.getUsername());
		
	}
	
	public ResponseEntity<Payment> createPayment(String username, Payment payment) throws MethodArgumentNotValidException {
		
		User user = repoU.findByUsername(username).get();
		
		user.setPayment(payment);
		repoU.save(user);
		
		return ResponseEntity.status(201).body(user.getPayment());
	}
	
}
