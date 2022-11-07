package com.cognixia.jump.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	public List<Order> findByCart(Set<Order> cart);
	
	public List<Order> findByUserId(String userId);
}
