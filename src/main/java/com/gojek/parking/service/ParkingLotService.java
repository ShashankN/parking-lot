package com.gojek.parking.service;

import com.gojek.parking.resource.Vehicle;

public interface ParkingLotService {
	
	public void createParkingLot(int totalSlots);
	
	public void parkVehicle(Vehicle vehicle);

	public void unparkVehicleFromSlotNumber(int slotNumber);

	public void printParkingLotStatus();

	public void printVehicleRegisterNumberOfColor(String color);

	public void printSlotNumbersOfColor(String colour);

	public void printSlotNoFromRegistrationNo(String registrationNo);
	
	public boolean isInitialized();

}
