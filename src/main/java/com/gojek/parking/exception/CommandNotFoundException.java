package com.gojek.parking.exception;

public class CommandNotFoundException extends Exception{
	
	private static final long serialVersionUID = 140914286786334508L;

	public CommandNotFoundException(String message) {
		super(message);
	}

}
