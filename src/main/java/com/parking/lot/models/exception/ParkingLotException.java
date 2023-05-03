package com.parking.lot.models.exception;

public class ParkingLotException extends Exception {

	private static final long serialVersionUID = 8458551832755512728L;

	public ParkingLotException() {
	}

	public ParkingLotException(String message) {
		super(message);
	}
}