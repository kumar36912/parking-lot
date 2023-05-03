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
import com.parking.lot.models.fee.calculator.mall.MallTwoWheelerFeeCalculator;
import com.parking.lot.models.manager.SlotManager;
import com.parking.lot.models.manager.VehicleSlotManager;
import com.parking.lot.models.strategy.fee.MallFeeStrategy;
import com.parking.lot.models.strategy.slot.allocation.AscOrderSlotAllocation;
import com.parking.lot.models.strategy.slot.allocation.SlotAllocationStrategy;

@TestMethodOrder(OrderAnnotation.class)
class SmallMotorCycleParkingLotTest {

	static ParkingLot smallParkingLot;

	@BeforeAll
	public static void init() {
		MallFeeCalculator mallTwoWheelerFeeCalculator = new MallTwoWheelerFeeCalculator();
		Map<VehicleType, MallFeeCalculator> vehTypeToMallFeeCalculator = new EnumMap<>(VehicleType.class);
		vehTypeToMallFeeCalculator.put(VehicleType.TWO_WHEELER, mallTwoWheelerFeeCalculator);
		MallFeeStrategy mallFeeStrategy = new MallFeeStrategy(vehTypeToMallFeeCalculator);

		Map<VehicleType, SlotManager> vehicleTypeToVehicleManager = new EnumMap<>(VehicleType.class);
		SlotAllocationStrategy naturalOrderSlotAllocation = new AscOrderSlotAllocation();
		SlotManager vehicleManager = new VehicleSlotManager(2, naturalOrderSlotAllocation);
		vehicleTypeToVehicleManager.put(VehicleType.TWO_WHEELER, vehicleManager);

		smallParkingLot = new ParkingLot(mallFeeStrategy, vehicleTypeToVehicleManager);
	}

	@Test
	@Order(1)
	void given2Slots_whenParkBike_thenReturnTicket() throws NoSlotAvailableException {
		ParkingTicket parkingTicket1 = smallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		assertNotNull(parkingTicket1);
		assertNotNull(parkingTicket1.getEntryTime());
		assertNotNull(parkingTicket1.getVehicleType());
		assertEquals(1, parkingTicket1.getParkingSlotNo());
		assertEquals(1, parkingTicket1.getTicketNumber());
		assertEquals(VehicleType.TWO_WHEELER, parkingTicket1.getVehicleType());
	}

	@Test
	@Order(2)
	void given1Slot_whenParkScooter_thenReturnTicket() throws NoSlotAvailableException {
		ParkingTicket parkingTicket2 = smallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		assertNotNull(parkingTicket2);
		assertNotNull(parkingTicket2.getEntryTime());
		assertNotNull(parkingTicket2.getVehicleType());
		assertEquals(2, parkingTicket2.getParkingSlotNo());
		assertEquals(2, parkingTicket2.getTicketNumber());
		assertEquals(VehicleType.TWO_WHEELER, parkingTicket2.getVehicleType());
	}

	@Test()
	@Order(3)
	void given0Slot_whenParkScooter_thenThrowException() throws NoSlotAvailableException {
		NoSlotAvailableException exception = assertThrows(NoSlotAvailableException.class, () -> {
			smallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		});
		assertNotNull(exception);
	}

	@Test
	@Order(4)
	void given0Slot_whenUnParkTicket2_thenReturnReceipt() throws NoSlotAvailableException {
		LocalDateTime exitTime = LocalDateTime.now().plusMinutes(50);
		ParkingReceipt parkingReceipt2 = smallParkingLot.unparkVehicle(2, exitTime);
		assertNotNull(parkingReceipt2);
		assertNotNull(parkingReceipt2.getEntryTime());
		assertEquals(10, parkingReceipt2.getFee());
		assertEquals(1, parkingReceipt2.getTicketNumber());
	}
	
	@Test
	@Order(5)
	void given1Slot_whenParkMotorCycle_thenReturnTicket() throws NoSlotAvailableException {
		ParkingTicket parkingTicket3 = smallParkingLot.parkVehicle(VehicleType.TWO_WHEELER);
		assertNotNull(parkingTicket3);
		assertNotNull(parkingTicket3.getEntryTime());
		assertNotNull(parkingTicket3.getVehicleType());
		assertEquals(2, parkingTicket3.getParkingSlotNo());
		assertEquals(3, parkingTicket3.getTicketNumber());
		assertEquals(VehicleType.TWO_WHEELER, parkingTicket3.getVehicleType());
	}
	
	@Test
	@Order(6)
	void given0Slot_whenUnParkTicket1_thenReturnReceipt() throws NoSlotAvailableException {
		LocalDateTime exitTime = LocalDateTime.now().plusHours(3).plusMinutes(50);
		ParkingReceipt parkingReceipt2 = smallParkingLot.unparkVehicle(1, exitTime);
		assertNotNull(parkingReceipt2);
		assertNotNull(parkingReceipt2.getEntryTime());
		assertEquals(40, parkingReceipt2.getFee());
		assertEquals(2, parkingReceipt2.getTicketNumber());
	}

}
