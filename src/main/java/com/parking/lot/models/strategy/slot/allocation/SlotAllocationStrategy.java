package com.parking.lot.models.strategy.slot.allocation;

import com.parking.lot.models.exception.NoSlotAvailableException;

/**
 * This interface is used to determine the slot allocation and deallocation
 * strategy. For now we have only AscOrderSlotAllocation supported. This can be
 * extended in the future to support other types of slot allocation strategies.
 **/
public interface SlotAllocationStrategy {

	public void addSlot(Integer slotNumber);

	public void removeSlot(Integer slotNumber);

	public int getNextSlot() throws NoSlotAvailableException;

	public boolean isSlotAvailable();
}
