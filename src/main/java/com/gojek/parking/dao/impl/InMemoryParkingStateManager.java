package com.gojek.parking.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gojek.parking.resource.Vehicle;
import com.gojek.parking.dao.ParkingLotStateManager;
import com.gojek.parking.exception.SlotNotParkedException;
import com.gojek.parking.exception.SlotUnavailbleException;
import com.gojek.parking.exception.VehicleAlreadyParkedException;
import com.gojek.parking.exception.VehicleNotFoundException;
import com.gojek.parking.service.SlotAllocationStrategy;

public class InMemoryParkingStateManager implements ParkingLotStateManager {

	private SlotAllocationStrategy slotAllocationStrategy;

	private Map<Integer, Vehicle> parkedVehicleMap;

	public InMemoryParkingStateManager(SlotAllocationStrategy slotAllocationStrategy) {
		this.slotAllocationStrategy = slotAllocationStrategy;
		this.parkedVehicleMap = new HashMap<>();
	}

	public int parkVehicle(Vehicle vehicle) throws SlotUnavailbleException, VehicleAlreadyParkedException {
		if (isVehicleAlreadyParked(vehicle)) {
			throw new VehicleAlreadyParkedException();
		}
		int slotNumber = slotAllocationStrategy.getNextFreeSlot();
		slotAllocationStrategy.markAsAllocated(slotNumber);
		parkedVehicleMap.put(slotNumber, vehicle);
		return slotNumber;
	}

	public int unparkVehicle(int slotNumber) throws SlotNotParkedException {
		if (parkedVehicleMap.containsKey(slotNumber)) {
			parkedVehicleMap.remove(slotNumber);
			slotAllocationStrategy.markAsFree(slotNumber);
			return slotNumber;
		}
		throw new SlotNotParkedException("No vehicle parked in "+slotNumber);
	}

	public List<String> getStatus() {
		if (parkedVehicleMap.isEmpty()) {
			return Arrays.asList("No vehicles parked");
		}
		List<String> statusList = new ArrayList<String>();
		Set<Entry<Integer, Vehicle>> entrySet = this.parkedVehicleMap.entrySet();
		statusList.add("Slot No.    Registration No    Colour");
		for (Entry<Integer, Vehicle> entry : entrySet) {
			statusList.add(String.format("%d           %s      %s", entry.getKey(),
					entry.getValue().getRegistrationNum(), entry.getValue().getColor()));
		}
		return statusList;
	}

	public List<String> getVehicleNumbersForColor(String color) {
		List<String> matchedVehicleNums = new ArrayList<>();
		Set<Entry<Integer, Vehicle>> entrySet = parkedVehicleMap.entrySet();
		for (Entry<Integer, Vehicle> entry : entrySet) {
			if(entry.getValue().getColor().equals(color)) {
				matchedVehicleNums.add(entry.getValue().getRegistrationNum());
			}
		}
		return matchedVehicleNums;
	}

	public List<Integer> getSlotNumbersForColor(String colour) {
		Set<Entry<Integer, Vehicle>> entrySet = parkedVehicleMap.entrySet();
		List<Integer> slotNumbers = new ArrayList<>();
		for (Entry<Integer, Vehicle> entry : entrySet) {
			if (entry.getValue().getColor().equals(colour)) {
				slotNumbers.add(entry.getKey());
			}
		}
		return slotNumbers;
	}

	public int getSlotNumberFromRegistrationNumber(String registrationNo) throws VehicleNotFoundException {
		Set<Entry<Integer, Vehicle>> entrySet = parkedVehicleMap.entrySet();
		for (Entry<Integer, Vehicle> entry : entrySet) {
			if (entry.getValue().getRegistrationNum().equals(registrationNo)) {
				return entry.getKey();
			}
		}
		throw new VehicleNotFoundException();
	}

	public boolean isVehiclesParked() {
		return parkedVehicleMap != null && !parkedVehicleMap.isEmpty();
	}

	
	private boolean isVehicleAlreadyParked(Vehicle vehicle) {
		Set<Entry<Integer, Vehicle>> entrySet = parkedVehicleMap.entrySet();
		for (Entry<Integer, Vehicle> entry : entrySet) {
			Vehicle parkedVehicle = entry.getValue();
			if(parkedVehicle.equals(vehicle)) {
				return true;
			}
		}
		return false;
	}

}
