package com.parking.lot.models.fee.calculator.mall;

import com.parking.lot.models.fee.calculator.FeeCalculator;

public interface MallFeeCalculator extends FeeCalculator {

	public default double calculateParkingFee(long hours, double baseFare) {
		return baseFare * (hours + 1);
	}
}
