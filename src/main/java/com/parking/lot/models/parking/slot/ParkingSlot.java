package com.parking.lot.models.parking.slot;

import java.time.LocalDateTime;

public class ParkingSlot {

	private int slotNumber;
	
	private LocalDateTime entryTime;
	
	public ParkingSlot(int slotNumber, LocalDateTime entryTime) {
		super();
		this.entryTime = entryTime;
		this.slotNumber = slotNumber;
	}
}
