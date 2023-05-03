package com.parking.lot.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.parking.lot.models.exception.NoSlotAvailableException;
import com.parking.lot.models.manager.SlotManager;
import com.parking.lot.models.strategy.fee.ParkingFeeStrategy;

public class ParkingLot {

	/**
	 * Various types of Fee Strategies (For eg. Mall, Stadium, Airport etc.) can be
	 * dependency injected to dynamically alter the behavior.
	 **/
	private ParkingFeeStrategy parkingFeeStrategy;

	/**
	 * We will be maintaining a collection of Vehicle slot managers depending on the
	 * VehicleType.
	 */
	private Map<VehicleType, SlotManager> vehicleTypeToVehicleManager;

	/**
	 * This map holds the Parking ticket which belongs to a particular ticket
	 * number. We need to maintain this mapping so that whenever a vehicle is
	 * unparked using the ticket number, all the relevant info can be fetched to
	 * calculate parking fare
	 **/
	private Map<Integer, ParkingTicket> ticketNumberToTicket;

	private SeriesGenerator seriesGenerator;

	public ParkingLot(ParkingFeeStrategy parkingFeeStrategy,
			Map<VehicleType, SlotManager> vehicleTypeToVehicleManager) {
		super();
		this.parkingFeeStrategy = parkingFeeStrategy;
		this.vehicleTypeToVehicleManager = vehicleTypeToVehicleManager != null ? vehicleTypeToVehicleManager
				: new HashMap<>();
		this.ticketNumberToTicket = new HashMap<>();
		this.seriesGenerator = new SeriesGenerator();
	}

	public ParkingTicket parkVehicle(VehicleType vehicleType) throws NoSlotAvailableException {
		SlotManager vehicleManager = this.vehicleTypeToVehicleManager.get(vehicleType);
		if (vehicleManager.isSlotAvailable()) {
			int slotNumber = vehicleManager.getNextSlot();
			ParkingTicket ticket = vehicleManager.parkVehicle(vehicleType, slotNumber,
					seriesGenerator.getTicketNumber());
			ticketNumberToTicket.put(ticket.getTicketNumber(), ticket);
			return ticket;
		} else {
			throw new NoSlotAvailableException("No parking slot available for the vehicle type " + vehicleType);
		}
	}

	public ParkingReceipt unparkVehicle(int parkingTicketNumber, LocalDateTime exitTime) {
		ParkingTicket ticket = ticketNumberToTicket.get(parkingTicketNumber);
		double parkingFare = parkingFeeStrategy.calculateParkingFee(ticket.getVehicleType(), ticket.getEntryTime(),
				exitTime);
		SlotManager vehicleManager = this.vehicleTypeToVehicleManager.get(ticket.getVehicleType());
		vehicleManager.unParkVehicle(ticket.getParkingSlotNo());
		ticketNumberToTicket.remove(ticket.getTicketNumber());
		return new ParkingReceipt(seriesGenerator.getReceiptNumber(), ticket.getEntryTime(), exitTime, parkingFare);
	}

}
