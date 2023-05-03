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
import com.parking.lot.models.fee.calculator.mall.MallFeeCalculator;
import com.parking.lot.models.fee.calculator.mall.MallFourWheelerFeeCalculator;
import com.parking.lot.models.fee.calculator.mall.MallHeavyWheelerFeeCalculator;
import com.parking.lot.models.fee.calculator.mall.MallTwoWheelerFeeCalculator;
import com.parking.lot.models.manager.SlotManager;
import com.parking.lot.models.manager.VehicleSlotManager;
import com.parking.lot.models.strategy.fee.MallFeeStrategy;
import com.parking.lot.models.strategy.slot.allocation.AscOrderSlotAllocation;
import com.parking.lot.models.strategy.slot.allocation.SlotAllocationStrategy;

@TestMethodOrder(OrderAnnotation.class)
class MallParkingLotTest {

	static ParkingLot mallParkingLot;

	@BeforeAll
	public static void init() {
		MallFeeCalculator mallTwoWheelerFeeCalculator = new MallTwoWheelerFeeCalculator();
		MallFeeCalculator mallFourWheelerFeeCalculator = new MallFourWheelerFeeCalculator();
		MallFeeCalculator mallHeavyWheelerFeeCalculator = new MallHeavyWheelerFeeCalculator();

		Map<VehicleType, MallFeeCalculator> vehTypeToMallFeeCalculator = new EnumMap<>(VehicleType.class);
		vehTypeToMallFeeCalculator.put(VehicleType.TWO_WHEELER, mallTwoWheelerFeeCalculator);
		vehTypeToMallFeeCalculator.put(VehicleType.FOUR_WHEELER, mallFourWheelerFeeCalculator);
		vehTypeToMallFeeCalculator.put(VehicleType.HEAVY_WHEELER, mallHeavyWheelerFeeCalculator);

		MallFeeStrategy mallFeeStrategy = new MallFeeStrategy(vehTypeToMallFeeCalculator);

		Map<VehicleType, SlotManager> vehicleTypeToVehicleManager = new EnumMap<>(VehicleType.class);
		SlotAllocationStrategy twoWheelerAllocationStrategy = new AscOrderSlotAllocation();
		SlotAllocationStrategy fourWheelerAllocationStrategy = new AscOrderSlotAllocation();
		SlotAllocationStrategy heavyWheelerAllocationStrategy = new AscOrderSlotAllocation();

		SlotManager twoWheelerManager = new VehicleSlotManager(100, twoWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.TWO_WHEELER, twoWheelerManager);

		SlotManager fourWheelerManager = new VehicleSlotManager(80, fourWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.FOUR_WHEELER, fourWheelerManager);

		SlotManager heavyWheelerManager = new VehicleSlotManager(10, heavyWheelerAllocationStrategy);
		vehicleTypeToVehicleManager.put(VehicleType.HEAVY_WHEELER, heavyWheelerManager);

		mallParkingLot = new ParkingLot(mallFeeStrategy, vehicleTypeToVehicleManager);
	}

	@Test
	@Order(1)
	void parkBikeFor3Hrs30Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = mallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(3).plusMinutes(30);
		ParkingReceipt parkingReceipt = mallParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(40, parkingReceipt.getFee());
	}

	@Test
	@Order(2)
	void parkCarFor6Hrs1Min_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = mallParkingLot.parkVehicle(VehicleType.FOUR_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(6).plusMinutes(1);
		ParkingReceipt parkingReceipt = mallParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(140, parkingReceipt.getFee());
	}

	@Test
	@Order(3)
	void parkCarFor1Hrs59Mins_thenReturnFee() throws NoSlotAvailableException {
		ParkingTicket parkingTicket = mallParkingLot.parkVehicle(VehicleType.HEAVY_WHEELER);
		LocalDateTime entryTime = parkingTicket.getEntryTime();
		LocalDateTime exitTime = entryTime.plusHours(1).plusMinutes(59);
		ParkingReceipt parkingReceipt = mallParkingLot.unparkVehicle(parkingTicket.getTicketNumber(), exitTime);
		assertNotNull(parkingTicket);
		assertEquals(100, parkingReceipt.getFee());
	}

	@Test
	@Order(4)
	void given100Slots_park100Bikes_thenReturnFee() throws NoSlotAvailableException {
		for (int i = 1; i <= 100; i++) {
			ParkingTicket parkingTicket = mallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
			assertNotNull(parkingTicket);
		}
	}

	@Test
	@Order(5)
	void given0Slots_park1Bikes_thenThrowsException() throws NoSlotAvailableException {
		NoSlotAvailableException exception = assertThrows(NoSlotAvailableException.class, () -> {
			mallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		});
		assertNotNull(exception);
	}

}
