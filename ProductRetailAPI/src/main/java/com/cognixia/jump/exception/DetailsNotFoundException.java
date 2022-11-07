package com.cognixia.jump.exception;

public class DetailsNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DetailsNotFoundException(String msg, boolean str) {
		super(msg);
	}
	
	public DetailsNotFoundException(String detail) {
		super("Book with " + detail + " was not found amoung the existing catalog.");
	}

}
