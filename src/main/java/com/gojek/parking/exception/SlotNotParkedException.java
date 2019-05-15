package com.gojek.parking.exception;

public class SlotNotParkedException extends Exception{
	
	public SlotNotParkedException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 4799026140273791924L;

}
