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
import com.parking.lot.models.fee.calculator.airport.AirportFeeCalculator;
import com.parking.lot.models.fee.calculator.airport.AirportFourWheelerFeeCalculator;
import com.parking.lot.models.fee.calculator.airport.AirportTwoWheelerFeeCalculator;
import com.parking.lot.models.manager.SlotManager;
import com.parking.lot.models.manager.VehicleSlotManager;
import com.parking.lot.models.strategy.fee.AirportFeeStrategy;
import com.parking.lot.models.strategy.slot.allocation.AscOrderSlotAllocation;
import com.parking.lot.models.strategy.slot.allocation.SlotAllocationStrategy;

@TestMethodOrder(OrderAnnotation.class)
class AirportParkingLotTest {

	static ParkingLot airportParkingLot;

	@BeforeAll
	public static void init() {
		AirportFeeCalculator airportTwoWheelerFeeCalculator = new AirportTwoWheelerFeeCalculator();
		AirportFeeCalculator airportFourWheelerFeeCalculator = new AirportFourWheelerFeeCalculator();

		Map<VehicleType, AirportFeeCalculator> vehTypeToAirportFeeCalculator = new EnumMap<>(VehicleType.class);
		vehTypeToAirportFeeCalculator.put(VehicleType.TWO_WHEELER, airportTwoWheelerFeeCalculator);
		vehTypeToAirportFeeCalculator.put(VehicleType.FOUR_WHEELER, airportFourWheelerFeeCalculator);

		AirportFeeStrategy airportFeeStrategy = new AirportFeeStrategy(vehTypeToAirportFeeCalculator);

		Map<VehicleType, SlotManager> vehicleTypeToVehicleManager = new EnumMap<>(VehicleType.class);
		SlotAllocationStrategy twoWheelerAllocationStrategy = new AscOrderSlotAllocation();
		SlotAllocationStrategy fourWheelerAllocationStrategy = new AscOrderSlotAllocation();

		SlotManager twoWheelerManager = new VehicleSlotManager(200, twoWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.TWO_WHEELER, twoWheelerManager);

		SlotManager fourWheelerManager = new VehicleSlotManager(500, fourWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.FOUR_WHEELER, fourWheelerManager);

		airportParkingLot = new ParkingLot(airportFeeStrategy, vehicleTypeToVehicleManager);
	}

	@Test
	@Order(1)
	void parkBikeFor0Hrs55Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(0).plusMinutes(55);
		ParkingReceipt parkingReceipt = airportParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(0, parkingReceipt.getFee());
	}

	@Test
	@Order(2)
	void parkBikeFor14Hrs59Min_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(14).plusMinutes(59);
		ParkingReceipt parkingReceipt = airportParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(60, parkingReceipt.getFee());
	}

	@Test
	@Order(3)
	void parkCarFor1Day12Hrs_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusDays(1).plusHours(12);
		ParkingReceipt parkingReceipt = airportParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(160, parkingReceipt.getFee());
	}

	@Test
	@Order(4)
	void parkCarFor50Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.FOUR_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusMinutes(50);
		ParkingReceipt parkingReceipt = airportParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(60, parkingReceipt.getFee());
	}
	
	@Test
	@Order(5)
	void parkCarFor23Hrs59Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.FOUR_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(23).plusMinutes(59);
		ParkingReceipt parkingReceipt = airportParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(80, parkingReceipt.getFee());
	}
	
	@Test
	@Order(6)
	void parkCarFor3Days1Hr_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.FOUR_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusDays(3).plusHours(1);
		ParkingReceipt parkingReceipt = airportParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(400, parkingReceipt.getFee());
	}

	@Test
	@Order(7)
	void given200Slots_park200Bikes_thenReturnFee() throws NoSlotAvailableException {
		for (int i = 1; i <= 200; i++) {
			ParkingTicket parkingTicket = airportParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
			assertNotNull(parkingTicket);
		}
	}

	@Test
	@Order(8)
	void given0Slots_park1Bikes_thenReturnFee() throws NoSlotAvailableException {
		NoSlotAvailableException exception = assertThrows(NoSlotAvailableException.class, () -> {
			airportParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		});
		assertNotNull(exception);
	}

}
