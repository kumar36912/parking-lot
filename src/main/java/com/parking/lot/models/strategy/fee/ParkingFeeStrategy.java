package com.parking.lot.models.strategy.fee;

import java.time.LocalDateTime;

import com.parking.lot.models.VehicleType;

public interface ParkingFeeStrategy {

	public double calculateParkingFee(VehicleType vehicleType, LocalDateTime entryTime, LocalDateTime exitTime);
}
