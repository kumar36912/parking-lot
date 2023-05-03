package com.parking.lot.models.strategy.fee;

import java.time.LocalDateTime;
import java.util.Map;

import com.parking.lot.models.VehicleType;
import com.parking.lot.models.fee.calculator.airport.AirportFeeCalculator;

public class AirportFeeStrategy implements ParkingFeeStrategy{

	private Map<VehicleType, AirportFeeCalculator> feeCalculators;

	public AirportFeeStrategy(Map<VehicleType, AirportFeeCalculator> feeCalculators) {
		super();
		this.feeCalculators = feeCalculators;
	}

	@Override
	public double calculateParkingFee(VehicleType vehicleType, LocalDateTime entryTime, LocalDateTime exitTime) {
		AirportFeeCalculator airportFeeCalculator = feeCalculators.get(vehicleType);
		return airportFeeCalculator.calculateParkingFee(entryTime, exitTime);
	}
}
