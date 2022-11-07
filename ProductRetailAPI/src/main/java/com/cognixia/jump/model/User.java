package com.cognixia.jump.model;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	public static enum Role {
		ROLE_USER, ROLE_ADMIN
	}
	
	@Id
	@Indexed(unique = true)
	private String id;
	
	@Indexed(unique = true)
	private String username;

	@Size(min = 8, max = 25, message = "Must be at least 8 characters")
	private String password;

	@NotBlank
	@Size(min = 1, max = 256)
	@Indexed(unique = true)
	private String email;

	@NotBlank
	private String role;

	@AssertTrue 
	private boolean enabled;
	
	@Valid
	private Payment payment;
	
	public User() {
		
	}

	public User(String id,
			@Pattern(regexp = "[A-Z][a-z][9-0]{gt 5}$", message = "Must be at least 5 characters using only letters and numbers") String username,
			@Pattern(regexp = "{gt 8}$", message = "Must be at least 8 characters") String password,
			@NotBlank String email, String role, boolean enabled, Payment payment) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.enabled = enabled;
		this.payment = payment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", role="
				+ role + ", enabled=" + enabled + ", payment=" + payment +  "]";
	}
	
}
