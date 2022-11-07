package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

	
	public Cart findByUserIdAndBookId(String userId, String bookId);
	
	public List<Cart> findByBookId(String bookId);

	public List<Cart> findByUserId(String userId);
	
	public List<Cart> findByTitleLike(String title);
	
	public List<Cart> findByPages(int pages);
	
	public List<Cart> findByDate(String date);
	
	@Query("{ 'genre': { $regex: '^?0$', $options: 'i' } }")
	public List<Cart> findByGenre(String genre);
	
	@Query("{ 'medium': { $regex: '^?0$', $options: 'i' } }")
	public List<Cart> findByMedium(String medium);
	
	@Query("{ 'author.name': { $regex: '^?0$', $options: 'i' } }")
	public List<Cart> findByName(String name);
	
	@Query("{ 'author.published': { ^?0$'} }")
	public List<Cart> findByPublished(int published);
	
	@Query("{ 'author.gender': { $regex: '^?0$', $options: 'i' } }")
	public List<Cart> findByGender(char gender);

	@Query("{ 'author.age': ?0 }")
	public List<Cart> findByAge(int age);

}
