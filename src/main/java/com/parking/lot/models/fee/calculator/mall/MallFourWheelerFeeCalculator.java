package com.parking.lot.models.fee.calculator.mall;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MallFourWheelerFeeCalculator implements MallFeeCalculator{

	@Override
	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
		long parkingHours = ChronoUnit.HOURS.between(entryTime, exitTime);
		double baseFare = 20;
		return calculateParkingFee(parkingHours, baseFare);
	}

}
