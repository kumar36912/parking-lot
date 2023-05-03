package com.parking.lot.models.fee.calculator.stadium;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StadiumFourWheelerFeeCalculator implements StadiumFeeCalculator {

	@Override
	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
		long parkingHours = ChronoUnit.HOURS.between(entryTime, exitTime);
		return calculateParkingFee(parkingHours, 60, 120, 200);
	}

}
