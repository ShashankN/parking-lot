package com.gojek.parking.serviceimpl;

import java.util.TreeSet;

import com.gojek.parking.exception.SlotUnavailbleException;
import com.gojek.parking.service.SlotAllocationStrategy;

public class NearestFreeSlotAllocationStrategy implements SlotAllocationStrategy {

	private TreeSet<Integer> freeSlots;

	private int slotsCount;

	public NearestFreeSlotAllocationStrategy(int slotsCount) {
		if (slotsCount < 1) {
			throw new IllegalArgumentException("Minimum slots required is 1, given " + slotsCount);
		}
		this.slotsCount = slotsCount;
		freeSlots = new TreeSet<>();
		for (int i = 1; i <= slotsCount; i++) {
			freeSlots.add(i);
		}
	}

	public void markAsAllocated(int slotNumber) {
		validateSlotNumber(slotNumber);
		if (!freeSlots.remove(slotNumber)) {
			throw new IllegalArgumentException("Slot already allocated");
		}
	}

	public void markAsFree(int slotNumber) {
		validateSlotNumber(slotNumber);
		freeSlots.add(slotNumber);
	}

	public int getNextFreeSlot() throws SlotUnavailbleException {
		if (hasSlot())
			return freeSlots.first();
		throw new SlotUnavailbleException();
	}

	public boolean hasSlot() {
		return !freeSlots.isEmpty();
	}

	public int getFreeSlotsCount() {
		return freeSlots.size();
	}

	private void validateSlotNumber(int slotNumber) {
		if (slotNumber < 1) {
			throw new IllegalArgumentException("Slot number cannot be less than 1 given " + slotNumber);
		} else if (slotNumber > slotsCount) {
			throw new IllegalArgumentException(
					"Slot number can only be between 1 and " + slotsCount + " given " + slotNumber);
		}
	}
}
