package com.parking.lot.models;

import java.time.LocalDateTime;

public abstract class Acknowledgement {

	private int serialNumber;
	
	private LocalDateTime entryTime;

	protected Acknowledgement(int serialNumber, LocalDateTime entryTime) {
		super();
		this.serialNumber = serialNumber;
		this.entryTime = entryTime;
	}
	
	public int getTicketNumber() {
		return this.serialNumber;
	}
	
	public LocalDateTime getEntryTime() {
		return this.entryTime;
	}
	
}
