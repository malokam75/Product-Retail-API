package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(404).body(errorDetails);
	}
	
	// Valid exception thrown when data doesn't fit validation annotation 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		
		StringBuilder errors = new StringBuilder("");
		for(FieldError fe : ex.getBindingResult().getFieldErrors()) {
			errors.append( "[" + fe.getField() + " : " + fe.getDefaultMessage() + "]; " );
		}
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(), request.getDescription(false) );
		
		return ResponseEntity.status(400).body(errorDetails);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<?> duplicateResource(DuplicateResourceException ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(400).body(errorDetails);
	}
	
	@ExceptionHandler(DetailsNotFoundException.class)
	public ResponseEntity<?> detailsNotFoundException(DetailsNotFoundException ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(404).body(errorDetails);
	}
}
