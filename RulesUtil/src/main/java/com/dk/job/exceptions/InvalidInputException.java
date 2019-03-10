package com.dk.job.exceptions;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidInputException(String message) {
		this.message = message;
	}

	public String toString() {
		return message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
