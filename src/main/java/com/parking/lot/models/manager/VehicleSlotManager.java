package com.parking.lot.models.manager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.parking.lot.models.ParkingTicket;
import com.parking.lot.models.VehicleType;
import com.parking.lot.models.exception.NoSlotAvailableException;
import com.parking.lot.models.parking.slot.ParkingSlot;
import com.parking.lot.models.strategy.slot.allocation.SlotAllocationStrategy;

public class VehicleSlotManager extends SlotManager {

	private Map<Integer, ParkingSlot> parkingSlotNoToSlot;

	public VehicleSlotManager(int maxNoOfSlots, SlotAllocationStrategy slotAllocationStrategy) {
		super(maxNoOfSlots, slotAllocationStrategy);
		parkingSlotNoToSlot = new HashMap<>();
		for (int i = 1; i <= maxNoOfSlots; i++) {
			slotAllocationStrategy.addSlot(i);
		}
	}

	public boolean isSlotAvailable() {
		return slotAllocationStrategy.isSlotAvailable();
	}

	public int getNextSlot() throws NoSlotAvailableException {
		return slotAllocationStrategy.getNextSlot();
	}

	public void removeSlot(Integer slotNumber) {
		slotAllocationStrategy.removeSlot(slotNumber);
	}

	public ParkingTicket parkVehicle(VehicleType vehicleType, int slotNumber, int ticketNumber) {
		LocalDateTime localDateTime = LocalDateTime.now();
		ParkingSlot parkingSlot = new ParkingSlot(slotNumber, localDateTime);
		slotAllocationStrategy.removeSlot(slotNumber);
		parkingSlotNoToSlot.put(slotNumber, parkingSlot);
		return new ParkingTicket(ticketNumber, localDateTime, slotNumber, vehicleType);
	}

	public void unParkVehicle(int parkingSlotNo) {
		slotAllocationStrategy.addSlot(parkingSlotNo);
		parkingSlotNoToSlot.remove(parkingSlotNo);
	}
}
