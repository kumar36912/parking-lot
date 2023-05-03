package com.parking.lot.models.manager;

import com.parking.lot.models.ParkingTicket;
import com.parking.lot.models.VehicleType;
import com.parking.lot.models.exception.NoSlotAvailableException;
import com.parking.lot.models.strategy.slot.allocation.SlotAllocationStrategy;

public abstract class SlotManager {

	protected int maxNoOfSlots;

	/**
	 * We are maintaining SlotAllocationStrategy inside SlotManager, because in
	 * future if we want to have the slots alloted in a different way based on the
	 * VehicleType (For eg: Random parking slot allocation, privileged parking slot
	 * allocation), the code will be easily extensible.
	 **/
	protected SlotAllocationStrategy slotAllocationStrategy;

	public abstract boolean isSlotAvailable();

	public abstract int getNextSlot() throws NoSlotAvailableException;

	public abstract void removeSlot(Integer slotNumber);

	public abstract ParkingTicket parkVehicle(VehicleType vehicleType, int slotNumber, int ticketNumber);

	public abstract void unParkVehicle(int parkingSlotNo);

	protected SlotManager(int maxNoOfSlots, SlotAllocationStrategy slotAllocationStrategy) {
		super();
		this.maxNoOfSlots = maxNoOfSlots;
		this.slotAllocationStrategy = slotAllocationStrategy;
	}

}
