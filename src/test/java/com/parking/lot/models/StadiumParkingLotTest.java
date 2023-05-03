package com.parking.lot.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.parking.lot.models.exception.NoSlotAvailableException;
import com.parking.lot.models.fee.calculator.stadium.StadiumFeeCalculator;
import com.parking.lot.models.fee.calculator.stadium.StadiumFourWheelerFeeCalculator;
import com.parking.lot.models.fee.calculator.stadium.StadiumTwoWheelerFeeCalculator;
import com.parking.lot.models.manager.SlotManager;
import com.parking.lot.models.manager.VehicleSlotManager;
import com.parking.lot.models.strategy.fee.StadiumFeeStrategy;
import com.parking.lot.models.strategy.slot.allocation.AscOrderSlotAllocation;
import com.parking.lot.models.strategy.slot.allocation.SlotAllocationStrategy;

@TestMethodOrder(OrderAnnotation.class)
class StadiumParkingLotTest {

	static ParkingLot stadiumParkingLot;

	@BeforeAll
	public static void init() {
		StadiumFeeCalculator stadiumTwoWheelerFeeCalculator = new StadiumTwoWheelerFeeCalculator();
		StadiumFeeCalculator stadiumFourWheelerFeeCalculator = new StadiumFourWheelerFeeCalculator();

		Map<VehicleType, StadiumFeeCalculator> vehTypeToStadiumFeeCalculator = new EnumMap<>(VehicleType.class);
		vehTypeToStadiumFeeCalculator.put(VehicleType.TWO_WHEELER, stadiumTwoWheelerFeeCalculator);
		vehTypeToStadiumFeeCalculator.put(VehicleType.FOUR_WHEELER, stadiumFourWheelerFeeCalculator);

		StadiumFeeStrategy mallFeeStrategy = new StadiumFeeStrategy(vehTypeToStadiumFeeCalculator);

		Map<VehicleType, SlotManager> vehicleTypeToVehicleManager = new EnumMap<>(VehicleType.class);
		SlotAllocationStrategy twoWheelerAllocationStrategy = new AscOrderSlotAllocation();
		SlotAllocationStrategy fourWheelerAllocationStrategy = new AscOrderSlotAllocation();

		SlotManager twoWheelerManager = new VehicleSlotManager(1000, twoWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.TWO_WHEELER, twoWheelerManager);

		SlotManager fourWheelerManager = new VehicleSlotManager(1500, fourWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.FOUR_WHEELER, fourWheelerManager);

		stadiumParkingLot = new ParkingLot(mallFeeStrategy, vehicleTypeToVehicleManager);
	}

	@Test
	@Order(1)
	void parkBikeFor3Hrs40Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = stadiumParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(3).plusMinutes(40);
		ParkingReceipt parkingReceipt = stadiumParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(30, parkingReceipt.getFee());
	}

	@Test
	@Order(2)
	void parkBikeFor14Hrs59Min_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = stadiumParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(14).plusMinutes(59);
		ParkingReceipt parkingReceipt = stadiumParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(390, parkingReceipt.getFee());
	}

	@Test
	@Order(3)
	void parkCarFor11Hrs30Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = stadiumParkingLot.parkVehicle(VehicleType.FOUR_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(11).plusMinutes(30);
		ParkingReceipt parkingReceipt = stadiumParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(180, parkingReceipt.getFee());
	}
	
	@Test
	@Order(4)
	void parkCarFor13Hrs5Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = stadiumParkingLot.parkVehicle(VehicleType.FOUR_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(13).plusMinutes(5);
		ParkingReceipt parkingReceipt = stadiumParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(580, parkingReceipt.getFee());
	}

	@Test
	@Order(5)
	void given1000Slots_park1000Bikes_thenReturnFee() throws NoSlotAvailableException {
		for (int i = 1; i <= 1000; i++) {
			ParkingTicket parkingTicket = stadiumParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
			assertNotNull(parkingTicket);
		}
	}

	@Test
	@Order(6)
	void given0Slots_park1Bikes_thenReturnFee() throws NoSlotAvailableException {
		NoSlotAvailableException exception = assertThrows(NoSlotAvailableException.class, () -> {
			stadiumParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		});
		assertNotNull(exception);
	}

}
