package com.cs414.parking;

public class Garage {
	public static final String MSG_PARKING_NOT_AVAILABLE = "\t\t\tSORRY PARKING FULL";
	public static final String MSG_PARKING_SLIP_HEADER = "\t\t\tCity of Fort Collins" + "\n" +
			"\t\t\t====================" + "\n";
	private final int CAPACITY = 100;
	private int occupied;
	final Transaction transaction = new Transaction();

	public int getCapacity() {
		return CAPACITY;
	}

	public int getOccupied() {
		return occupied;
	}

	public String handleEntry(final String vechicleNum) {

		if(parkingAvailable()) {
			occupied ++;
			return MSG_PARKING_SLIP_HEADER + transaction.createTransaction(vechicleNum);
		} else {
			return MSG_PARKING_SLIP_HEADER + MSG_PARKING_NOT_AVAILABLE;
		}
	}



	public boolean parkingAvailable() {
		return occupied < CAPACITY;
	}

	// @VisibleForTesting  -- Wanted  to use this annotation, but not sure if its available on the lab machines
	//Testing support
	public void setOccupied(final int occupied) {
		this.occupied = occupied;
	}


}
