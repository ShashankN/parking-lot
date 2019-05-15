package com.gojek.parking.daoimpl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.gojek.parking.resource.Vehicle;
import com.gojek.parking.daoimpl.InMemoryParkingStateManager;
import com.gojek.parking.exception.SlotUnavailbleException;
import com.gojek.parking.exception.VehicleAlreadyParkedException;
import com.gojek.parking.exception.VehicleNotFoundException;
import com.gojek.parking.serviceimpl.NearestFreeSlotAllocationStrategy;

public class InMemoryParkingStateManagerTests {

	private InMemoryParkingStateManager parkingStateManager;
	
	@Before
	public void init() throws SlotUnavailbleException, VehicleAlreadyParkedException {
		parkingStateManager = new InMemoryParkingStateManager(new NearestFreeSlotAllocationStrategy(3));
		parkingStateManager.parkVehicle(new Vehicle("KA-55-1234", "RED"));
	}
	
	@Test
	public void whenParkCalledShouldReturnNearestSlot() throws SlotUnavailbleException, VehicleAlreadyParkedException {
		assertEquals(2,parkingStateManager.parkVehicle(new Vehicle("KA-55-3786", "RED")));
	}
	
	@Test
	public void whenGetSlotNumCalledForExistingRegNumShouldReturnSlotNum() throws VehicleNotFoundException {
		assertEquals(1, parkingStateManager.getSlotNumberFromRegistrationNumber("KA-55-1234"));
	}
	
	@Test(expected=VehicleNotFoundException.class)
	public void whenGetSlotNumCalledForNonExistingRegNumShouldThrowException() throws VehicleNotFoundException {
		parkingStateManager.getSlotNumberFromRegistrationNumber("KA-55-9741");
	}
	
	 
}
