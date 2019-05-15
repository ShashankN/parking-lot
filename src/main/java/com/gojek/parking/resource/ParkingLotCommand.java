package com.gojek.parking.resource;

import com.gojek.parking.exception.CommandNotFoundException;

public enum ParkingLotCommand {
	CREATE("create_parking_lot", 1), PARK("park", 2), LEAVE("leave", 1), STATUS("status", 0), SLOT_NUM_FOR_COLOR(
			"slot_numbers_for_cars_with_colour", 1), SLOT_NUM_FOR_REG_NUM("slot_number_for_registration_number",
					1), REG_NO_FOR_COLOR("registration_numbers_for_cars_with_colour", 1);

	private String command;
	private int numOfArguments;

	private ParkingLotCommand(String command, int numOfArguments) {
		this.command = command;
		this.numOfArguments = numOfArguments;
	}

	public String getCommand() {
		return command;
	}

	public int getNumOfArguments() {
		return numOfArguments;
	}


	public static ParkingLotCommand getCommand(String commad) throws CommandNotFoundException {
		//TODO
		return null;
	}

}
