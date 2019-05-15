package com.gojek.parking.service;

import com.gojek.parking.exception.CommandNotFoundException;
import com.gojek.parking.exception.IllegalCommandException;
import com.gojek.parking.exception.InvalidCommandException;

public interface ParkingLotCommandProcessor {
	
	public void validateCommand(String command) throws InvalidCommandException;
	public void executeCommand(String command) throws IllegalCommandException, CommandNotFoundException;
}
