package com.cognixia.jump.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Cart;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.CartRepository;
import com.cognixia.jump.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

	@Mock
	private CartRepository repo;
	
	@Mock
	private UserRepository repoU;
	
	@InjectMocks
	private CartService service;
	
	@InjectMocks
	private BookService serviceB;
	
	@Test
	void testGetCart() throws Exception {
		User user = new User("1", "user1", "password123", "user1@gmail.com", "ROLE_USER", true, null); 
		List<Cart> cart = new ArrayList<Cart>();
		cart.add( new Cart("1", user.getId(), user.getUsername(), "1", "The Good & the Evil", null, 234, "09-23-2008", null, 3, 23.99, null) );
		cart.add( new Cart("2", user.getId(), user.getUsername(), "2", "Ten Scary stories", null, 85, "10-31-2000", null, 5, 12.99,  null) );
		
		when( repo.findByUserId(user.getId()) ).thenReturn(cart);
		
		List<Cart> result = service.getCart(user.getUsername());
		
		if( cart.size() != result.size() ) {
			fail();
		}
		
		for(int i=0; i<cart.size(); i++) {
			Cart og = cart.get(i);
			Cart rt = result.get(i);
			
			if( !og.equals(rt) ) {
				fail();
			}
		}
	}

	@Test
	void testGetBookCountInCart() throws Exception {
	
		User user = new User("1", "user1", "password123", "user1@gmail.com", "ROLE_USER", true, null); 
//		List<Cart> cart = new ArrayList<Cart>();
//		cart.add( new Cart("1", user.getId(), user.getUsername(), "1", "The Good & the Evil", null, 234, "09-23-2008", null, 3, 23.99, null) );
//		cart.add( new Cart("2", user.getId(), user.getUsername(), "2", "Ten Scary stories", null, 85, "10-31-2000", null, 5, 12.99,  null) );
		List<Book> books = new ArrayList<Book>();
		books.add( new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null) );
		books.add( new Book("2", "Ten Scary stories", null, 25, "10-31-2000", null, 157, 12.99,  null) );
		
//		Cart cart = new Cart("1", userId, username, bookId, "The Good & the Evil", null, 234, "09-23-2008", null, 3, 23.99, null);
		service.createCart(books.get(0).getId(), user.getUsername());
		service.createCart(books.get(1).getId(), user.getUsername());
		
		int good = 1;
		when( repo.findByUserId(user.getId()).get(0).getQty() ).thenReturn( good );
		
		int result = service.getBookCountInCart(user.getUsername(), null);
				
		if(result == 0) {
			fail();
		} else {
			System.out.println(result);
		}
	}
	
//	@Test
//	void testGetBook() throws Exception {
//		
//		Optional<Cart> book = Optional.of(new Cart("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null));
//		String id = "1";
//		
//		when( repo.findById(id) ).thenReturn(book);
//		ResponseEntity<?> result = service.getBook(id);
////		assertThrows( ResourceNotFoundException.class, () -> {
////			service.getBook(id);
////		} );
//		if(result != null) {
//			fail();
//		} else {
//			System.out.println("deleted");
//		}
//	}
	
	@Test
	void testCreateBook() throws Exception {
		
		String bookId = "1";
		String userId = "1";
		String username = "user1";
//		User user = new User("1", "user1", "password123", "user1@gmail.com", "ROLE_USER", true, null); 
		
		Book book = new Book(bookId, "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null);
//		serviceB.createBook(book);
		
		Cart cart = new Cart("1", userId, username, bookId, "The Good & the Evil", null, 234, "09-23-2008", null, 3, 23.99, null);
//		service.createCart(bookId, username);
		
		when( repo.save(Mockito.any(Cart.class)) ).thenReturn(cart);
		
		ResponseEntity<Cart> result = service.createCart(bookId, username);
				
		if(result == null) {
			fail();
		} else {
			System.out.println("created");
		}
	}
		
	@Test
	void testDeleteCart() throws Exception {
		
		String bookId = "1";
		String userId = "1";
		String username = "user1";
//		User user = new User("1", "user1", "password123", "user1@gmail.com", "ROLE_USER", true, null); 
		Book book = new Book("1", "The Good & the Evil", null, 234, "09-23-2008", null, 345, 23.99, null);
//		serviceB.createBook(book);
		
		Cart cart = new Cart("1", userId, username, bookId, "The Good & the Evil", null, 234, "09-23-2008", null, 3, 23.99, null);
				
//		when( repo.save(Mockito.any(Cart.class)) ).thenReturn(cart);
		
		ResponseEntity<?> result = service.createCart(cart.getBookId(), username);
		result = service.deleteCart(bookId, username);
				
		if(result != null) {
			fail();
		} else {
			System.out.println("deleted");
		}
	}
	
}
