package com.cognixia.jump.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.mongodb.core.mapping.Document;

import nonapi.io.github.classgraph.json.Id;

@Document
public class Order {

	@Id
	private String id;
	
	@NotBlank
	private String userId;
	
	@NotBlank
	private String user;
	
	@NotBlank
	private List<Cart>  cart;
	
	@Pattern( regexp = "[0-9]{$gt: 1.00}$", message = "Must be a dollar and cent amount." )
	private double totPrice;
	
	@Valid
	private Payment payment;
	
	public Order() {
		this(null, null, null, null, 0.00, null);
	}

	public Order(String id, @NotBlank String userId, @NotBlank String user, @NotBlank List<Cart> cart,
			@Pattern(regexp = "[0-9]{$gt: 1.00}$", message = "Must be a dollar and cent amount.") double totPrice,
			@Valid Payment payment) {
		super();
		this.id = id;
		this.userId = userId;
		this.user = user;
		this.cart = cart;
		this.totPrice = totPrice;
		this.payment = payment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart>  cart) {
		this.cart = cart;
	}

	public double getTotPrice() {
		return totPrice;
	}

	public void setTotPrice(double totPrice) {
		this.totPrice = totPrice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", user=" + user + ", cart=" + cart + ", totPrice=" + totPrice
				+ ", payment=" + payment + "]";
	}
	
}
