package com.parking.lot.models.strategy.fee;

import java.time.LocalDateTime;
import java.util.Map;

import com.parking.lot.models.VehicleType;
import com.parking.lot.models.fee.calculator.mall.MallFeeCalculator;

public class MallFeeStrategy implements ParkingFeeStrategy{

	private Map<VehicleType, MallFeeCalculator> feeCalculators;

	public MallFeeStrategy(Map<VehicleType, MallFeeCalculator> feeCalculators) {
		super();
		this.feeCalculators = feeCalculators;
	}

	@Override
	public double calculateParkingFee(VehicleType vehicleType, LocalDateTime entryTime, LocalDateTime exitTime) {
		MallFeeCalculator mallFeeCalculator = feeCalculators.get(vehicleType);
		return mallFeeCalculator.calculateParkingFee(entryTime, exitTime);
	}
	
}
