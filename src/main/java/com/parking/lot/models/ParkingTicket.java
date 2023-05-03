package com.parking.lot.models;

import java.time.LocalDateTime;

public class ParkingTicket extends Acknowledgement {

	public ParkingTicket(int serialNumber, LocalDateTime entryTime, int parkingSlotNo, VehicleType vehicleType) {
		super(serialNumber, entryTime);
		this.parkingSlotNo = parkingSlotNo;
		this.vehicleType = vehicleType;
	}

	private int parkingSlotNo;
	
	private VehicleType vehicleType;
	
	public VehicleType getVehicleType() {
		return this.vehicleType;
	}

	public int getParkingSlotNo() {
		return parkingSlotNo;
	}

	public void setParkingSlotNo(int parkingSlotNo) {
		this.parkingSlotNo = parkingSlotNo;
	}
	
}
