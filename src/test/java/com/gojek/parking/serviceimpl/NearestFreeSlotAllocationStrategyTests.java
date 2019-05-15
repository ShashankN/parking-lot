package com.gojek.parking.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gojek.parking.exception.SlotUnavailbleException;
import com.gojek.parking.service.SlotAllocationStrategy;
import com.gojek.parking.serviceimpl.NearestFreeSlotAllocationStrategy;

public class NearestFreeSlotAllocationStrategyTests {
	
	@Test
	public void whenInitialisedWithSlotCount10FreeSlotShouldBe10() {
		SlotAllocationStrategy strategy = new NearestFreeSlotAllocationStrategy(10);
		assertEquals(10, strategy.getFreeSlotsCount());
	}
	
	@Test
	public void whenAskedForFreeSlotShouldGetNearestSlotFirst() throws SlotUnavailbleException {
		SlotAllocationStrategy strategy = new NearestFreeSlotAllocationStrategy(10);
		int slotNum = strategy.getNextFreeSlot();
		assertEquals(1, slotNum);
		strategy.markAsAllocated(slotNum);
		slotNum = strategy.getNextFreeSlot();
		assertEquals(2, slotNum);
		strategy.markAsAllocated(slotNum);
		strategy.markAsFree(1);
		assertEquals(1, strategy.getNextFreeSlot());
	}
	
	@Test
	public void whenAllocatedFreeSlotCountShouldDecrease() throws SlotUnavailbleException {
		SlotAllocationStrategy strategy = new NearestFreeSlotAllocationStrategy(10);
		assertEquals(10, strategy.getFreeSlotsCount());
		strategy.markAsAllocated(strategy.getNextFreeSlot());
		assertEquals(9, strategy.getFreeSlotsCount());
	}
	
	@Test
	public void whenAllSlotsAreAllocatedHasSlotShouldReturnFalse() {
		SlotAllocationStrategy strategy = new NearestFreeSlotAllocationStrategy(10);
		for(int i=1;i<=10;i++) {
			strategy.markAsAllocated(i);
		}
		assertFalse(strategy.hasSlot());	
	}
	
	@Test
	public void whenSlotsAreAvailableHashSlotShoulReturnTrue() {
		SlotAllocationStrategy strategy = new NearestFreeSlotAllocationStrategy(10);
		for(int i=1;i<=5;i++) {
			strategy.markAsAllocated(i);
		}
		assertTrue(strategy.hasSlot());	
	}
}
