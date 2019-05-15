package com.gojek.parking.service;

import com.gojek.parking.exception.SlotUnavailbleException;

public interface SlotAllocationStrategy {

	public void markAsAllocated(int slotNumber);

	public void markAsFree(int slotNumber);

	public int getNextFreeSlot() throws SlotUnavailbleException;

	public boolean hasSlot();
	
	public int getFreeSlotsCount();

}
