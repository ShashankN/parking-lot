package com.gojek.parking.resource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gojek.parking.exception.CommandNotFoundException;

public class ParkingLotCommandTests {

	@Test
	public void testCorrectEnumCommands() throws CommandNotFoundException {
		assertEquals(ParkingLotCommand.CREATE, ParkingLotCommand.getCommand("create_parking_lot"));
		assertEquals(ParkingLotCommand.LEAVE, ParkingLotCommand.getCommand("leave"));
		assertEquals(ParkingLotCommand.PARK, ParkingLotCommand.getCommand("park"));
		assertEquals(ParkingLotCommand.SLOT_NUM_FOR_COLOR,
				ParkingLotCommand.getCommand("slot_numbers_for_cars_with_colour"));
		assertEquals(ParkingLotCommand.SLOT_NUM_FOR_REG_NUM,
				ParkingLotCommand.getCommand("slot_number_for_registration_number"));
		assertEquals(ParkingLotCommand.REG_NO_FOR_COLOR,
				ParkingLotCommand.getCommand("registration_numbers_for_cars_with_colour"));
		assertEquals(ParkingLotCommand.STATUS, ParkingLotCommand.getCommand("status"));
	}

	@Test(expected = CommandNotFoundException.class)
	public void testIncorrectCommands() throws CommandNotFoundException {
		ParkingLotCommand.getCommand("create parking lot");
	}

}
