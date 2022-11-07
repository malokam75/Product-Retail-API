package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.DetailsNotFoundException;
import com.cognixia.jump.exception.DuplicateResourceException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "book", description = "the API for managing books")
public class BookController {

	@Autowired
	BookRepository repo;
	
	@Autowired
	BookService service;
	
	@Operation( summary = "Get all the books in the book table",
				description = "Gets all the books from the book table in the "
						+ "product_db database. Each book grabbed has an "
						+ "id, title, genre, pages, date, medium, qty, price, and author of the book.")
	@GetMapping("/book")
	public List<Book> getBooks() throws ResourceNotFoundException {
		return service.getBooks();
	}
	
	@GetMapping("/book/count")
	public long getBookCount() {
		return repo.count();
	}
	
	@ApiResponse(responseCode = "200", description = "Book has been found")
	@ApiResponse(responseCode = "404", description = "Book was not found")
	@GetMapping("/book/id")
	public ResponseEntity<?> getBook(@RequestParam String id) throws ResourceNotFoundException {
		
		return service.getBook(id);
	}
	
	@PostMapping("/book")
	public ResponseEntity<?> createBook(@RequestBody Book book) throws MethodArgumentNotValidException, DuplicateResourceException, ResourceNotFoundException, NullPointerException {
		
		return service.createBook(book);
	}

	@DeleteMapping("/book")
	public ResponseEntity<?> deleteBook(@RequestParam String id) throws ResourceNotFoundException {
		
		return service.deleteBook(id);
	}
	
	@PutMapping("/book")
	public ResponseEntity<?> updateBook(@RequestBody Book book) throws ResourceNotFoundException, MethodArgumentNotValidException {
		
		return service.updateBook(book);
	}
	
	@GetMapping("/book/title")
	public List<Book> getByTitle(@RequestParam String title) throws DetailsNotFoundException {
		if (repo.findByTitleLike(title).isEmpty()) {
			throw new DetailsNotFoundException("title: " + title);
		}
		return repo.findByTitleLike(title);
	}
	
	@GetMapping("/book/genre")
	public List<Book> getByGenre(@RequestParam String genre) throws DetailsNotFoundException {
		if (repo.findByGenre(genre).isEmpty()) {
			throw new DetailsNotFoundException("genre: " + genre);
		}
		return repo.findByGenre(genre);
	}
	
	@GetMapping("/book/medium/{medium}")
	public List<Book> getByMedium(@PathVariable String medium) throws DetailsNotFoundException {
		if (repo.findByMedium(medium).isEmpty()) {
			throw new DetailsNotFoundException("medium: " + medium);
		}
		return repo.findByMedium(medium);
	}
	
	@GetMapping("/book/pages/{pages}")
	public List<Book> getByPages(@PathVariable int pages) throws DetailsNotFoundException {
		if (repo.findByPages(pages).isEmpty()) {
			throw new DetailsNotFoundException(pages + " pages");
		}
		return repo.findByPages(pages);
	}
	
	@GetMapping("/book/date/{date}")
	public List<Book> getByDate(@PathVariable String date) throws DetailsNotFoundException {
		if (repo.findByDate(date).isEmpty()) {
			throw new DetailsNotFoundException("date: " + date);
		}
		return repo.findByDate(date);
	}
	
	@GetMapping("/book/author/name/{name}")
	public List<Book> getByName(@PathVariable String name) throws DetailsNotFoundException {
		if (repo.findByName(name).isEmpty()) {
			throw new DetailsNotFoundException("author's name: " + name);
		}
		return repo.findByName(name);
	}
	
	@GetMapping("/book/author/age/{age}")
	public List<Book> getByAge(@PathVariable int age) throws DetailsNotFoundException {
		if (repo.findByAge(age).isEmpty()) {
			throw new DetailsNotFoundException("author's age: " + age);
		}
		return repo.findByAge(age);
	}
	
	@GetMapping("/book/author/published/{published}")
	public List<Book> getByPublished(@PathVariable int published) throws DetailsNotFoundException {
		if (repo.findByPublished(published).isEmpty()) {
			throw new DetailsNotFoundException("author publishing: " + published + " titles");
		}
		return repo.findByPublished(published);
	}
	
	@GetMapping("/book/author/gender/{gender}")
	public List<Book> getByGender(@Valid @PathVariable String gender) throws DetailsNotFoundException, MethodArgumentNotValidException {

		if (repo.findByGender(gender).isEmpty()) {
			throw new DetailsNotFoundException("Book with author's gender: "
					+ gender + " was not found amoung the existing catalog. "
							+ "Hint: keep gender as a single character.", true);
		}
		return repo.findByGender(gender);
	}
}
