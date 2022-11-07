package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Cart;

@Repository
public interface BookRepository extends MongoRepository<Book, String>{
	
	public Cart findBookById(String id);
	
	public List<Book> findByTitleLike(String title);
	
	public List<Book> findByPages(int pages);
	
	public List<Book> findByDate(String date);
	
	@Query("{ 'genre': { $regex: '^?0$', $options: 'i' } }")
	public List<Book> findByGenre(String genre);
	
	@Query("{ 'medium': { $regex: '^?0$', $options: 'i' } }")
	public List<Book> findByMedium(String medium);
	
	@Query("{ 'author.name': { $regex: '^?0$', $options: 'i' } }")
	public List<Book> findByName(String name);
	
	@Query("{ 'author.published': ?0 }")
	public List<Book> findByPublished(int published);
	
	@Query("{ 'author.gender': { $regex: '^?0$', $options: 'i' } }")
	public List<Book> findByGender(String gender);

	@Query("{ 'author.age': ?0 }")
	public List<Book> findByAge(int age);

	public boolean existsByTitle(String title);
	
}
