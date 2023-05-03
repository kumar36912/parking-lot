package com.parking.lot.models.fee.calculator;

import java.time.LocalDateTime;

public interface FeeCalculator {

	public double calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime);
}
