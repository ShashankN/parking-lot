package com.gojek.parking.serviceimpl;

import com.gojek.parking.resource.Vehicle;
import com.gojek.parking.dao.ParkingLotStateManager;
import com.gojek.parking.dao.impl.InMemoryParkingStateManager;
import com.gojek.parking.exception.SlotNotParkedException;
import com.gojek.parking.exception.SlotUnavailbleException;
import com.gojek.parking.exception.VehicleAlreadyParkedException;
import com.gojek.parking.exception.VehicleNotFoundException;
import com.gojek.parking.service.ParkingLotService;
import com.gojek.parking.service.SlotAllocationStrategy;

import static com.gojek.parking.constants.MessageConstants.*;

import java.util.ArrayList;
import java.util.List;

public class SingleLevelParkingService implements ParkingLotService {

	private ParkingLotStateManager lotStateManager;

	public void parkVehicle(Vehicle vehicle) {
		try {
			int slotNumber = lotStateManager.parkVehicle(vehicle);
			System.out.println(String.format(SLOT_ALLOCATED_MSG, slotNumber));
		} catch (SlotUnavailbleException e) {
			System.out.println(SLOT_FULL_MSG);
		} catch (VehicleAlreadyParkedException e) {
			System.out.println(String.format(VEHICLE_ALREADY_PARKED_MSG, vehicle.getRegistrationNum()));
		}
	}

	public void unparkVehicleFromSlotNumber(int slotNumber) {
		try {
			int freeSlot = lotStateManager.unparkVehicle(slotNumber);
			System.out.println(String.format(SLOT_FREE_MSG, freeSlot));
		} catch (SlotNotParkedException e) {
			System.out.println(String.format(SLOT_NOT_PARKED_MSG, slotNumber));
		}
	}

	public void printParkingLotStatus() {
		List<String> statusList = lotStateManager.getStatus();
		for (String status : statusList) {
			System.out.println(status);
		}
	}

	public void printVehicleRegisterNumberOfColor(String color) {
		List<String> vehicleNumbersForColor = lotStateManager.getVehicleNumbersForColor(color);
		if (vehicleNumbersForColor.isEmpty()) {
			System.out.println();
		} else {
			for (String vehicle : vehicleNumbersForColor) {
				System.out.println(vehicle);
			}
		}

	}

	public void printSlotNumbersOfColor(String colour) {
		List<Integer> slotNumbersForColor = lotStateManager.getSlotNumbersForColor(colour);

		if (slotNumbersForColor.isEmpty()) {
			System.out.println(String.format(CARS_WITH_COLOR_NOT_FOUND_MSG, colour));
		} else {
			List<String> slotNumbersStr = new ArrayList<String>();
			for (Integer slotNum : slotNumbersForColor) {
				slotNumbersStr.add(String.valueOf(slotNum));
			}
			System.out.println(String.join(", ", slotNumbersStr));
		}
	}

	public void printSlotNoFromRegistrationNo(String registrationNo) {
		try {
			int allocatedSlotNum = lotStateManager.getSlotNumberFromRegistrationNumber(registrationNo);
			System.out.println(allocatedSlotNum);
		} catch (VehicleNotFoundException e) {
			System.out.println(VEHICLE_NOT_FOUND);
		}
	}

	public void createParkingLot(int slotsCount) {
		SlotAllocationStrategy slotAllocationstrategy = new NearestFreeSlotAllocationStrategy(slotsCount);
		this.lotStateManager = new InMemoryParkingStateManager(slotAllocationstrategy);
		System.out.println(String.format(PARKING_LOT_CREATED_MSG, slotsCount));
	}

	public boolean isInitialized() {
		return lotStateManager!=null;
	}

}
