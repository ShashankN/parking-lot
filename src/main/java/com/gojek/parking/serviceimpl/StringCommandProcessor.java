package com.gojek.parking.serviceimpl;

import com.gojek.parking.resource.ParkingLotCommand;
import com.gojek.parking.resource.Vehicle;
import com.gojek.parking.exception.CommandNotFoundException;
import com.gojek.parking.exception.InvalidCommandException;
import com.gojek.parking.service.ParkingLotCommandProcessor;
import com.gojek.parking.service.ParkingLotService;

public class StringCommandProcessor implements ParkingLotCommandProcessor {

	private ParkingLotService parkingLotService;

	public void validateCommand(String command) throws InvalidCommandException {
		if (command == null) {
			throw new InvalidCommandException("Command cannot be null");
		}
		String tokens[] = command.split(" ");
		String commandToken = tokens[0];
		try {
			ParkingLotCommand parkingCommand = ParkingLotCommand.getCommand(commandToken);
			if (parkingCommand.getNumOfArguments() != tokens.length - 1) {
				throw new InvalidCommandException("Command " + parkingCommand.getCommand() + " expects only "
						+ parkingCommand.getNumOfArguments() + " given " + (tokens.length - 1));
			}
		} catch (CommandNotFoundException e) {
			throw new InvalidCommandException("Invalid command " + command);
		}

	}

	public void executeCommand(String commandStr) throws CommandNotFoundException {
		String tokens[] = commandStr.split(" ");
		ParkingLotCommand command = ParkingLotCommand.getCommand(tokens[0]);
		switch (command) {
		case CREATE:
			createParkingLot(tokens[1]);
			break;
		case PARK:
			validateState();
			parkVehicle(tokens[1], tokens[2]);
			break;
		case LEAVE:
			validateState();
			leave(tokens[1]);
			break;
		case SLOT_NUM_FOR_COLOR:
			validateState();
			getSlotNumForColor(tokens[1]);
			break;
		case SLOT_NUM_FOR_REG_NUM:
			validateState();
			getSlotNumForRegNum(tokens[1]);
			break;
		case REG_NO_FOR_COLOR:
			validateState();
			getRegNumForColor(tokens[1]);
			break;
		case STATUS:
			validateState();
			getStatus();
			break;
		default:
			break;
		}
	}

	private void getStatus() {
		parkingLotService.printParkingLotStatus();
	}

	private void getSlotNumForRegNum(String regNum) {
		parkingLotService.printSlotNoFromRegistrationNo(regNum);
	}

	private void getSlotNumForColor(String color) {
		parkingLotService.printSlotNumbersOfColor(color);
	}

	private void getRegNumForColor(String color) {
		parkingLotService.printVehicleRegisterNumberOfColor(color);
	}

	private void parkVehicle(String regNumber, String color) {
		Vehicle vehicle = new Vehicle(regNumber, color);
		parkingLotService.parkVehicle(vehicle);
	}

	private void leave(String slotNumStr) {
		parkingLotService.unparkVehicleFromSlotNumber(Integer.parseInt(slotNumStr));
	}

	private void createParkingLot(String slotCount) {
		parkingLotService = new SingleLevelParkingService();
		parkingLotService.createParkingLot(Integer.parseInt(slotCount));
	}

	private void validateState() {
		if (parkingLotService == null || !parkingLotService.isInitialized()) {
			throw new IllegalStateException("Parking lot service not initialized");
		}
	}
}
