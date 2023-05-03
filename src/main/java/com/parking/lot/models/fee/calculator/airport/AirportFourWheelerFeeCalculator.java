package com.parking.lot.models.fee.calculator.airport;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AirportFourWheelerFeeCalculator implements AirportFeeCalculator {

	@Override
	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
		long hours = ChronoUnit.HOURS.between(entryTime, exitTime);
		long days = ChronoUnit.DAYS.between(entryTime, exitTime);
		if (days == 0) {
			if (hours < 12) {
				return 60;
			} else {
				return 80;
			}
		} else {
			return (days + 1) * 100d;
		}
	}

}
