package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognixia.jump.exception.DuplicateResourceException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Author;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.service.BookService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

	@MockBean
	BookService service;
	
	@MockBean
	BookRepository repo;
	
	@InjectMocks
	BookController controller;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	void testGetBooks() throws Exception {
		
		String uri = "/api/book";
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	void testGetBooks1() throws Exception {
		
		List<Book> books = new ArrayList<Book>();
		Author author = new Author();
		
		books.add( new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, author) );
		books.add( new Book("2", "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99, author) );
		
		String uri = "/api/book";
		
		when( service.getBooks() ).thenReturn( books );
		
		mvc.perform( get(uri) )
			.andDo( print() )
			.andExpect( status().isOk() )
			.andExpect( jsonPath("$.length()").value( books.size() ) )
			.andExpect( jsonPath("$[0].id").value( books.get(0).getId() ) )	
			.andExpect( jsonPath("$[1].title").value( books.get(1).getTitle() ) )	
			.andExpect( jsonPath("$[0].author.name").value( books.get(0).getAuthor().getName() ) );
		
		verify( service, times(1) ).getBooks();
		
		verifyNoMoreInteractions( service );
	}
	
	@Test
	void testGetBookCount() throws Exception {
		
		List<Book> books = new ArrayList<Book>();
		Author author = new Author();
		
		books.add( new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, author) );
		books.add( new Book("2", "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99, author) );
		
		String uri = "/api/book/count";
		
		when( service.getBookCount() );//.thenReturn( service.getBookCount() );
		
		mvc.perform( get(uri) )
			.andDo( print() )
			.andExpect( status().isOk() );
			
		verify( service, times(1) ).getBookCount();
		verifyNoMoreInteractions(service);
		
	}
	
	@Test
	void testGetBook() throws Exception {
		
		String id = "1";
		String uri = "/api/book/id";
		
		when( service.getBook(id) ).thenThrow( new ResourceNotFoundException("Book", id) );
		
		mvc.perform( get(uri, id) )
			.andDo( print() )
			.andExpect( status().isNotFound() );
			
		verify( service, times(1) ).getBook(id);
		verifyNoMoreInteractions(service);
		
	}
	
	@Test
	void createBook() throws Exception {
		
		String uri = "/api/book";
		
		Book book = new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null);

		when( service.createBook( Mockito.any(Book.class) ) ).thenThrow( new DuplicateResourceException("Book", book.getId()) );
		
		mvc.perform( post(uri) )
			.andDo( print() )
			.andExpect( status().isNotFound() );
			
		verify( service, times(1) ).createBook(book);
		verifyNoMoreInteractions(service);
		
	}
	
	@Test
	void deleteBook() throws Exception {
		
		String uri = "/api/book";
		
		Book book = new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null);

		when( service.deleteBook(book.getId()) ).thenThrow( new ResourceNotFoundException("Book", book.getId()) );
		
		mvc.perform( delete(uri) )
			.andDo( print() )
			.andExpect( status().isNotFound() );
			
		verify( service, times(1) ).deleteBook(book.getId());
		verifyNoMoreInteractions(service);
		
	}
	
}
