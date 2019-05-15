package com.gojek.parking.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gojek.parking.exception.CommandNotFoundException;
import com.gojek.parking.exception.IllegalCommandException;
import com.gojek.parking.exception.InvalidCommandException;
import com.gojek.parking.service.ParkingLotCommandProcessor;

public class StringCommandProcessorTests {

	private static String LINE_BREAK = String.format("%n");
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void endToEndTest() throws InvalidCommandException, IllegalCommandException, CommandNotFoundException {
		ParkingLotCommandProcessor commandProcessor = new StringCommandProcessor();
		String inputLines[] = new String[] { "create_parking_lot 6", "park KA-01-HH-1234 White",
				"park KA-01-HH-9999 White", "park KA-01-BB-0001 Black", "park KA-01-HH-7777 Red",
				"park KA-01-HH-2701 Blue", "park KA-01-HH-3141 Black", "leave 4", "status", "park KA-01-P-333 White",
				"park DL-12-AA-9999 White", "registration_numbers_for_cars_with_colour White",
				"slot_numbers_for_cars_with_colour White", "slot_number_for_registration_number KA-01-HH-3141",
				"slot_number_for_registration_number MH-04-AY-1111" };

		for (int i = 0; i < inputLines.length; i++) {
			String command = String.format(inputLines[i]);
			commandProcessor.validateCommand(command);
			commandProcessor.executeCommand(command);
		}

		String output = outContent.toString();
		String lines[] = output.split(LINE_BREAK);
		String expectedRes[] = new String[] { "Created a parking lot with 6 slots", "Allocated slot number: 1",
				"Allocated slot number: 2", "Allocated slot number: 3", "Allocated slot number: 4",
				"Allocated slot number: 5", "Allocated slot number: 6", "Slot number 4 is free",
				"Slot No.    Registration No    Colour", "1           KA-01-HH-1234      White",
				"2           KA-01-HH-9999      White", "3           KA-01-BB-0001      Black",
				"5           KA-01-HH-2701      Blue", "6           KA-01-HH-3141      Black",
				"Allocated slot number: 4", "Sorry, parking lot is full", "KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333",
				"1, 2, 4", "6", "Not found" };
		for (int i = 0; i < lines.length; i++) {
			assertEquals(expectedRes[i], lines[i]);
		}

	}
	
	//TODO add more test
}
