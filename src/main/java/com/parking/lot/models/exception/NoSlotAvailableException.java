package com.parking.lot.models.exception;

public class NoSlotAvailableException extends ParkingLotException {

	public NoSlotAvailableException() {
		super();
	}

	public NoSlotAvailableException(String exceptionMsg) {
		super(exceptionMsg);
	}

}
