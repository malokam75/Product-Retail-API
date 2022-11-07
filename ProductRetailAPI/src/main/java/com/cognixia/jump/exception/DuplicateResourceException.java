package com.cognixia.jump.exception;

public class DuplicateResourceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DuplicateResourceException(String msg) {
		super(msg);
	}
	
	public DuplicateResourceException(String resource, String id) {
		super(resource + " was already found with id = " + id);
	}
}
