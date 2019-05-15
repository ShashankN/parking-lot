package com.gojek.parking.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gojek.parking.resource.Vehicle;

public class SingleLevelParkingServiceTest {

	
	private static String LINE_BREAK = String.format("%n");
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	private SingleLevelParkingService parkingService;

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
	@Before
	public void setupParkingService() {
		parkingService = new SingleLevelParkingService();
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	
	@Test
	public void whenCreateParkingSlotIsCallShouldCreateAParkingLotWithGivenSlotNum() {
		parkingService.createParkingLot(3);
		assertEquals("Created a parking lot with 3 slots"+LINE_BREAK, outContent.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenSlotNumberIsLessThan1() {
		parkingService.createParkingLot(0);
	}
	
	@Test
	public void shouldAllocateSlotWhenParkIsCalled() throws IOException {
		parkingService.createParkingLot(2);
		parkingService.parkVehicle(new Vehicle("KA-55 1234", "RED"));
		String res = outContent.toString().split(LINE_BREAK)[1];
		assertEquals("Allocated slot number: 1"+LINE_BREAK.trim(),res.trim());
	}
	
	@Test
	public void shouldPrintLeaveWhenUnparked() {
		parkingService.createParkingLot(2);
		parkingService.parkVehicle(new Vehicle("KA-55 1234", "RED"));
		parkingService.unparkVehicleFromSlotNumber(1);
		String res = outContent.toString().split(LINE_BREAK)[2];
		assertEquals("Slot number 1 is free"+LINE_BREAK.trim(),res.trim());
	}
	
	
	@Test
	public void shouldPrintStatusWhenPrintIsCalled() {
		parkingService.createParkingLot(2);
		parkingService.parkVehicle(new Vehicle("KA-01-HH-1234", "White"));
		parkingService.printParkingLotStatus();
		String actual = outContent.toString().split(LINE_BREAK)[2]+LINE_BREAK+outContent.toString().split(LINE_BREAK)[3];
		String expected = "Slot No.    Registration No    Colour"+LINE_BREAK+"1           KA-01-HH-1234      White";
		assertEquals(expected, actual);
	}
	
	//TODO add more cases
	
}
