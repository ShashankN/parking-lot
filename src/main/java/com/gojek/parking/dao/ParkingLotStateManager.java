package com.gojek.parking.dao;

import java.util.List;

import com.gojek.parking.exception.SlotNotParkedException;
import com.gojek.parking.exception.SlotUnavailbleException;
import com.gojek.parking.exception.VehicleAlreadyParkedException;
import com.gojek.parking.exception.VehicleNotFoundException;
import com.gojek.parking.resource.Vehicle;

public interface ParkingLotStateManager {

	public int parkVehicle(Vehicle vehicle) throws SlotUnavailbleException, VehicleAlreadyParkedException;

	public int unparkVehicle(int slotNumber) throws SlotNotParkedException;

	public List<String> getStatus();

	public List<String> getVehicleNumbersForColor(String color);

	public List<Integer> getSlotNumbersForColor(String colour);

	public int getSlotNumberFromRegistrationNumber(String registrationNo) throws VehicleNotFoundException;
}
