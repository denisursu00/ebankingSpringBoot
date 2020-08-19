package com.example.ebanking;

@SuppressWarnings("serial")
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}