package com.parking.lot.models.strategy.fee;

import java.time.LocalDateTime;
import java.util.Map;

import com.parking.lot.models.VehicleType;
import com.parking.lot.models.fee.calculator.stadium.StadiumFeeCalculator;

public class StadiumFeeStrategy implements ParkingFeeStrategy{

	private Map<VehicleType, StadiumFeeCalculator> feeCalculators;

	public StadiumFeeStrategy(Map<VehicleType, StadiumFeeCalculator> feeCalculators) {
		super();
		this.feeCalculators = feeCalculators;
	}


	@Override
	public double calculateParkingFee(VehicleType vehicleType, LocalDateTime entryTime, LocalDateTime exitTime) {
		StadiumFeeCalculator stadiumFeeCalculator = feeCalculators.get(vehicleType);
		return stadiumFeeCalculator.calculateParkingFee(entryTime, exitTime);
	}
	
}
