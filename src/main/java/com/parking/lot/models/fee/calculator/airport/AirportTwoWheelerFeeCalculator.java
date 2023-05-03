package com.parking.lot.models.fee.calculator.airport;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AirportTwoWheelerFeeCalculator implements AirportFeeCalculator {

	@Override
	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
		long hours = ChronoUnit.HOURS.between(entryTime, exitTime);
		long days = ChronoUnit.DAYS.between(entryTime, exitTime);
		if (days == 0) {
			if (hours < 1) {
				return 0;
			} else if (hours >= 1 && hours < 8) {
				return 40;
			} else {
				return 60;
			}
		} else {
			return (days + 1) * 80d;
		}
	}

}
