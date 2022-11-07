package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private Set<String> genre;
	
	@NotBlank
	private int pages;
	
	@Pattern( regexp = "[0-9]{8}$", message = "Must be in the following [MM-DD-YYYY] format." )
	private String date;
	
	@NotBlank
	private Set<String> medium;
	
	@NotBlank
	private int qty;
	
	@Range(min =  1, message = "Must be a dollar and cent amount." )
	private double price;
	
	@Valid
	private Author author;

	public Book() {
		this("N/A", "N/A", null, 0, "N/A", null, 0, 0.00, null);
	}
	
	public Book(String id, @NotBlank String title, @NotBlank Set<String> genre, @NotBlank int pages,
			@Pattern(regexp = "[0-9]{8}$", message = "Must be in the following [MM-DD-YYYY] format.") String date,
			@NotBlank Set<String> medium, @NotBlank int qty,
			@Range(min = 1, message = "Must be a dollar and cent amount.") double price, @Valid Author author) {
		super();
		this.id = id;
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
		return "Book [id=" + id + ", title=" + title + ", genre=" + genre + ", pages=" + pages + ", date=" + date
				+ ", medium=" + medium + ", qty=" + qty + ", price=" + price + ", author=" + author + "]";
	}
	
}
