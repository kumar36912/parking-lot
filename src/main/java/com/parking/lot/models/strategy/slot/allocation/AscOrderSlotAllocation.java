package com.parking.lot.models.strategy.slot.allocation;

import java.util.TreeSet;

import com.parking.lot.models.exception.NoSlotAvailableException;

public class AscOrderSlotAllocation implements SlotAllocationStrategy{
	
	private TreeSet<Integer> availableSlots;
	
	public AscOrderSlotAllocation() {
		availableSlots = new TreeSet<>();
	}

	@Override
	public void addSlot(Integer slotNumber) {
		this.availableSlots.add(slotNumber);
	}

	@Override
	public void removeSlot(Integer slotNumber) {
		this.availableSlots.remove(slotNumber);		
	}

	@Override
	public int getNextSlot() throws NoSlotAvailableException {
		if(this.availableSlots.isEmpty()) {
			throw new NoSlotAvailableException();
		}
		return this.availableSlots.first();
	}

	@Override
	public boolean isSlotAvailable() {
		return !availableSlots.isEmpty();
	}
	
	

}
