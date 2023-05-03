package com.parking.lot.models;

import java.time.LocalDateTime;

public class ParkingReceipt extends Acknowledgement {

	private double fee;
	
	private LocalDateTime exitTime;

	public ParkingReceipt(int serialNumber, LocalDateTime entryTime, LocalDateTime exitTime, double fee) {
		super(serialNumber, entryTime);
		this.fee = fee;
		this.exitTime = exitTime;
	}
	
	public double getFee() {
		return this.fee;
	}
	
}
