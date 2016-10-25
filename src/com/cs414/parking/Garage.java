package com.cs414.parking;


public class Garage {
	final String capacityFile= "GarageCapacity.txt";
	private int CAPACITY = readCapacityFromConfiguration();;
	private int currentOccupancy;
	final Transaction transaction = new Transaction();
	float amt = GarageConstants.AMOUNT_NOT_CALCULATED;

	public int getCapacity() {
		return CAPACITY;
	}

	public int getCurrentOccupancy() {
		return currentOccupancy;
	}

	public String handleEntry(final String vechicleNum) {

		if(isAvailable()) {
			currentOccupancy ++;
			return GarageConstants.MSG_PARKING_SLIP_HEADER + transaction.createTransaction(vechicleNum);
		} else {
			return GarageConstants.MSG_PARKING_SLIP_HEADER + GarageConstants.MSG_PARKING_NOT_AVAILABLE;
		}
	}

	public String handleExit(final String receiptNum) {
		try {
			final int receiptNumValid  = Integer.parseInt(receiptNum);
			amt = transaction.getReceiptDetails(receiptNumValid);
			if(amt == GarageConstants.AMOUNT_NOT_CALCULATED) {
				return GarageConstants.INVALID_RECEIPT_NUM;
			}
			return GarageConstants.AMNT_TO_PAY + amt;
		} catch (final NumberFormatException nfe) {
			return GarageConstants.INVALID_RECEIPT_NUM;
		}
	}
	
	public String makePayment(float amout) {
		if ((amt != GarageConstants.AMOUNT_NOT_CALCULATED) && (amout == amt))  {
			if(currentOccupancy > 0) { 
				currentOccupancy --;
			}
			amt = GarageConstants.AMOUNT_NOT_CALCULATED;
			return GarageConstants.EXIT_GARAGE_SUCCESSFULY;
		} else {
			return GarageConstants.EXIT_GARAGE_NOT_ALLOWED;
		}
	}
	
	public String handleMissingTicket() {
		amt = transaction.getAdminOverRideForMissingReceipt();
		return GarageConstants.AMNT_TO_PAY + amt;
	}
	

	public boolean isAvailable() {
		return currentOccupancy < CAPACITY;
	}
	
	public String updateParkingGarageSize(final int newSize) {
		GarageUtils.updateSingleValueInResourceFolder(capacityFile, newSize);
		return "Garage upgraded to " + newSize;
	}
	
	public int readCapacityFromConfiguration() {
		final int defaultParkingSize = 5;
		try {
			return Integer.parseInt(GarageUtils.readOneLineInResourceFolder(capacityFile));
		} catch(final NumberFormatException nfe) {
			//Default parking size.
			return defaultParkingSize;
		}
	}

	// @VisibleForTesting  -- Wanted  to use this annotation, but not sure if its available on the lab machines
		//Testing support
		public void setOccupied(final int occupied) {
			this.currentOccupancy = occupied;
		}
		
}
