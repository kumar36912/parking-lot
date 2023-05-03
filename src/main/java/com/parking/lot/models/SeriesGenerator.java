package com.parking.lot.models;

import java.util.concurrent.atomic.AtomicInteger;

public class SeriesGenerator {
	
	private final AtomicInteger ticketCounter = new AtomicInteger();
	
	private final AtomicInteger receiptCounter = new AtomicInteger();
	
	public int getTicketNumber() {
		return ticketCounter.incrementAndGet();
	}
	
	public int getReceiptNumber() {
		return receiptCounter.incrementAndGet();
	}
}
