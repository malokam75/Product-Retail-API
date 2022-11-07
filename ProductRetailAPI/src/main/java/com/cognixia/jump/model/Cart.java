package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotBlank
	private String userId;
	
	@NotBlank
	private String user;
	
	@NotBlank
	private String bookId;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private Set<String> genre;
	
	@Pattern( regexp = "[0-9]$", message = "Must be an integer value." )
	private int pages;
	
	@Pattern( regexp = "?$", message = "Must be in the following [MM-DD-YYYY] format." )
	private String date;
	
	@NotBlank
	private Set<String> medium;
	
	@Pattern( regexp = "[0-9]{$gt: 1}$", message = "Must be an integer value." )
	private int qty;
	
	@Pattern( regexp = "[0-9]{$gt: 1.00}$", message = "Must be a dollar and cent amount." )
	private double price;
	
	@Valid
	private Author author;

	public Cart() {
		this("N/A", "N/A", "N/A", "N/A", "N/A", null, 0, "N/A", null, 0, 0.00, null);
	}
	
	public Cart(String id, @NotBlank String userId, @NotBlank String user, @NotBlank String bookId,
			@NotBlank String title, @NotBlank Set<String> genre,
			@Pattern(regexp = "[0-9]$", message = "Must be an integer value.") int pages,
			@Pattern(regexp = "?$", message = "Must be in the following [MM-DD-YYYY] format.") String date,
			@NotBlank Set<String> medium,
			@Pattern(regexp = "[0-9]{$gt: 1}$", message = "Must be an integer value.") int qty,
			@Pattern(regexp = "[0-9]{$gt: 1.00}$", message = "Must be a dollar and cent amount.") double price,
			@Valid Author author) {
		super();
		this.id = id;
		this.userId = userId;
		this.user = user;
		this.bookId = bookId;
		this.title = title;
		this.genre = genre;
		this.pages = pages;
		this.date = date;
		this.medium = medium;
		this.qty = qty;
		this.price = price;
		this.author = author;
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
	
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getGenre() {
		return genre;
	}

	public void setGenre(Set<String> genre) {
		this.genre = genre;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Set<String> getMedium() {
		return medium;
	}

	public void setMedium(Set<String> medium) {
		this.medium = medium;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", userId=" + userId + ", user=" + user + ", bookId=" + bookId + ", title=" + title
				+ ", genre=" + genre + ", pages=" + pages + ", date=" + date + ", medium=" + medium + ", qty=" + qty
				+ ", price=" + price + ", author=" + author + "]";
	}
	
}
