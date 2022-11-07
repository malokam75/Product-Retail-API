package com.cognixia.jump.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@Mock
	private BookRepository repo;
	
	@InjectMocks
	private BookService service;
	
	@Test
	void testGetBooks() throws Exception {
		List<Book> books = new ArrayList<Book>();
		books.add( new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null) );
		books.add( new Book("2", "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99,  null) );
		
		when( repo.findAll() ).thenReturn(books);
		
		List<Book> result = service.getBooks();
		
		if( books.size() != result.size() ) {
			fail();
		}
		
		for(int i=0; i<books.size(); i++) {
			Book og = books.get(i);
			Book rt = result.get(i);
			
			if( !og.equals(rt) ) {
				fail();
			}
		}
	}

	@Test
	void testGetBookCount() throws Exception {
	
		List<Book> books = new ArrayList<Book>();
		books.add( new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null) );
		books.add( new Book("2", "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99,  null) );
		long i = 0;
		while (i<books.size()) {
			i++;
		}
		when( repo.count() ).thenReturn( i );
		
		long result = service.getBookCount();
				
		if(result == 0) {
			fail();
		} else {
			System.out.println(result);
		}
	}
	
	@Test
	void testGetBook() throws Exception {
		
		Optional<Book> book = Optional.of(new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null));
		String id = "1";
		
		when( repo.findById(id) ).thenReturn(book);
		ResponseEntity<?> result = service.getBook(id);
//		assertThrows( ResourceNotFoundException.class, () -> {
//			service.getBook(id);
//		} );
		if(result != null) {
			fail();
		} else {
			System.out.println("deleted");
		}
	}
	
	@Test
	void testCreateBook() throws Exception {
		String id1 = "1";
		String id2 = "2";
		List<Book> book = new ArrayList<Book>();
		book.add( new Book(id1, "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null) );
		book.add( new Book(id2, "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99,  null) );
		
		when( repo.save(Mockito.any(Book.class)) ).thenReturn(book.get(0));
		
		ResponseEntity<?> result = service.createBook(book.get(0));
				
		if(result == null) {
			fail();
		} else {
			System.out.println("created");
		}
	}
	
	@Test
	void testUpdateBook() throws Exception {
		String id1 = "1";
		List<Book> book = new ArrayList<Book>();
		book.add( new Book(id1, "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null) );

		when( repo.save(Mockito.any(Book.class)) ).thenReturn(book.get(0));
		
		ResponseEntity<?> result = service.updateBook(book.get(0));
				
		if(result == null) {
			fail();
		} else {
			System.out.println("updated");
		}
		
	}
		
	@Test
	void deleteBook() throws Exception {
		String id1 = "1";
		String id2 = "2";
		List<Book> book = new ArrayList<Book>();
		book.add( new Book(id1, "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null) );
		book.add( new Book(id2, "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99,  null) );
				
//		when( repo.deleteById(id) ).thenReturn(book);
//		service.createBook(book);
		repo.save(book.get(0));
		ResponseEntity<?> result = service.deleteBook(id1);
				
		if(result != null) {
			fail();
		} else {
			System.out.println("deleted");
		}
	}

}
