package com.cognixia.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Cart;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.CartRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class CartService {
		
	@Autowired
	CartRepository repo;
	
	@Autowired
	BookRepository repoB;
	
	@Autowired
	UserRepository repoU;
	
	public List<Cart> getCart(String username) throws ResourceNotFoundException {
		User user = repoU.findByUsername(username).get();
		
		if (repo.findByUserId(user.getId()) == null) {
			
			throw new ResourceNotFoundException(username + "'s cart is empty.");
		}
		return repo.findByUserId(user.getId());
	}

	public ResponseEntity<Cart> createCart(String bookId, String username) throws ResourceNotFoundException {
		User user = repoU.findByUsername(username).get();
		Cart entry = new Cart();
		
		if(repoB.existsById(bookId)) {
			Cart created = repo.findByUserIdAndBookId(user.getId(), bookId);
			
			if(created == null) {
				entry = newEntry(bookId, username);
			} else {
				entry = updateEntry(created.getQty()+1, bookId, user);
			}
			
			created = repo.save(entry);
			return ResponseEntity.status(201).body(created);
		}
		
		throw new ResourceNotFoundException("Book", bookId);
	}

	public Cart newEntry(String bookId, String username) throws ResourceNotFoundException {
		User user = repoU.findByUsername(username).get();
		Cart entry = repoB.findBookById(bookId);
				
		entry.setId(null);
		entry.setUserId(user.getId());
		entry.setUser(username);
		entry.setBookId(bookId);
		entry.setQty(1);

		return entry;
	}
	
	public Cart updateEntry(int qty, String bookId, User user) throws ResourceNotFoundException {
		
		Cart entry = repo.findByUserIdAndBookId(user.getId(), bookId);
		Book book = repoB.findById(bookId).get();
		
		if(book.getQty()<qty) {
			throw new ResourceNotFoundException("Cannot complete your request for " 
					+ book.getTitle() + ", there are only " 
					+ book.getQty() + " in inventory right now."
							+ "\nPlease adjust your qty request accordingly.");
		}
		
		if(entry != null) {
			entry.setQty(qty);
		} else { 
			entry = newEntry(bookId, user.getUsername());
			entry.setQty(qty);
		}
		return entry;
	}
	
	public ResponseEntity<?> updateQtyInCart(int qty, String bookId, String username) 
			throws ResourceNotFoundException, MethodArgumentNotValidException {
		
		User user = repoU.findByUsername(username).get();
		Cart entry = updateEntry(qty, bookId, user);	
		
		
		Cart updated = repo.save(entry);
		return ResponseEntity.status(201).body(updated);
	}

	public ResponseEntity<?> deleteCart(String bookId, String username) throws ResourceNotFoundException {
		User user = repoU.findByUsername(username).get();
		Cart found = repo.findByUserIdAndBookId(user.getId(), bookId);
		
		if(found == null) {
			throw new ResourceNotFoundException(user.getUsername() + "'s cart does not have the book: " + repoB.findBookById(bookId).getTitle());
		}
		repo.deleteById(found.getId());
		
		return ResponseEntity.status(201).body(found);
	}

	public int getBookCountInCart(String username, String bookId) {
		User user = repoU.findByUsername(username).get();

		if(bookId == null) {
			int count = 0;
			List<Cart> cart = repo.findByUserId(user.getId());
			for(int i=0; i<cart.size();i++) {
				count += cart.get(i).getQty();
			}
			return count;
		} else {
			return repo.findByBookId(bookId).size();
		}
	}

	
}
