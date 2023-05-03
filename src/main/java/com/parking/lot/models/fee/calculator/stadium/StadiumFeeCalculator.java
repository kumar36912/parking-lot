package com.parking.lot.models.fee.calculator.stadium;

import com.parking.lot.models.fee.calculator.FeeCalculator;

public interface StadiumFeeCalculator extends FeeCalculator {

	public default double calculateParkingFee(long parkingHours, double slabFee1, double slabFee2, double slabFee3) {
		if (parkingHours < 4) {
			return slabFee1;
		} else if (parkingHours >= 4 && parkingHours < 12) {
			return slabFee1 + slabFee2;
		} else {
			return slabFee1 + slabFee2 + (parkingHours - 11) * slabFee3;
		}
	}
}
