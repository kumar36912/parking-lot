package com.parking.lot.models.fee.calculator.mall;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MallTwoWheelerFeeCalculator implements MallFeeCalculator{

	@Override
	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
		long parkingHours = ChronoUnit.HOURS.between(entryTime, exitTime);
		double baseFare = 10;
		return calculateParkingFee(parkingHours, baseFare);
	}

}
