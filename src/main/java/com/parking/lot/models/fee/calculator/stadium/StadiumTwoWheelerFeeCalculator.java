package com.parking.lot.models.fee.calculator.stadium;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StadiumTwoWheelerFeeCalculator implements StadiumFeeCalculator {

	@Override
	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
		long parkingHours = ChronoUnit.HOURS.between(entryTime, exitTime);
		return calculateParkingFee(parkingHours, 30, 60, 100);
	}

}
