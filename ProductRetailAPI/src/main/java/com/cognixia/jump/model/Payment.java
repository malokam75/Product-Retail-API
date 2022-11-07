package com.cognixia.jump.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {

	@NotBlank
	@Size(min = 2, max = 35)
	private String name;
	
	@Pattern( regexp = "[0-9]{16}$", message = "Must be 16 digits long." )
	private String cardNumber;
	
	@Pattern( regexp = "[0-9]{4}$", message = "Must have 2 digits for month and 2 digits for the year.")
	@JsonProperty("expDate")
	private String expDate;
	
	@Pattern( regexp = "[0-9]{3}$", message = "Must be 3 digits long." )
	private String cvv;
	
	public Payment() {
		
	}
	
	public Payment(@NotBlank @Size(min = 2, max = 35) String name,
			@Pattern(regexp = "[0-9]{16}$", message = "Must be 16 digits long.") String cardNumber,
			@Pattern(regexp = "[0-9]{4}$", message = "Must have 2 digits for month and 2 digits for the year.") String expDate,
			@Pattern(regexp = "[0-9]{3}$", message = "Must be 3 digits long.") String cvv) {
		super();
		this.name = name;
		this.cardNumber = cardNumber;
		this.expDate = expDate;
		this.cvv = cvv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Override
	public String toString() {
		return "Payment [name=" + name + ", cardNumber=" + cardNumber + ", expDate=" + expDate + ", cvv=" + cvv + "]";
	}
	
}
