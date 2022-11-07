package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.cognixia.jump.exception.DuplicateResourceException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repo;
	
	public List<Book> getBooks() throws ResourceNotFoundException {
		
		if (repo.findAll() == null) {
			
			throw new ResourceNotFoundException("Book catalog is empty.");
		}
		return repo.findAll();
	}
	
	public ResponseEntity<?> getBook(String id) throws ResourceNotFoundException {
		
		if(repo.existsById(id)) {
			return ResponseEntity.status(200).body(repo.findById(id).get());
		}
		
		throw new ResourceNotFoundException("Book", id);
	}

	public ResponseEntity<?> createBook(Book book) throws MethodArgumentNotValidException, DuplicateResourceException, ResourceNotFoundException, NullPointerException {
		
		if( book == null ) {
			
			throw new NullPointerException("Book details are missing");
			
		} else if ( repo.existsByTitle( book.getTitle() )) {
			
			throw new DuplicateResourceException("The book: " + book.getTitle(),
					repo.findByTitleLike(book.getTitle()).get(0).getId());
			
		} else {
			book.setId(null);
			
			Book created = repo.save(book);
			
			return ResponseEntity.status(201).body(created);
		} 
		
	}

	public ResponseEntity<?> deleteBook(String id) throws ResourceNotFoundException {

		Optional<Book> found = repo.findById(id);
		
		if(found.isEmpty() ) {
			throw new ResourceNotFoundException("Book", id);
		}
		
		repo.deleteById(id);
		
		return ResponseEntity.status(200).body(found.get());
	}

	public ResponseEntity<?> updateBook(Book book) throws ResourceNotFoundException {
		
		if(repo.existsById(book.getId())) {
			Book updated = repo.save(book);
			
			return ResponseEntity.status(200).body(updated);
		}
		
		throw new ResourceNotFoundException("Book", book.getId());
	}

	public long getBookCount() {
		return repo.count();
	}
	
}
