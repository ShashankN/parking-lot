package com.gojek.parking.exception;

public class InvalidCommandException extends Exception {

	private static final long serialVersionUID = 7085336616364138553L;

	public InvalidCommandException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
