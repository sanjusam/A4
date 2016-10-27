package com.cs414.parking;

import java.util.Calendar;

public class PaymentGenerator {
	
	public float getTotalAmt(final Calendar startTime) {
		final int hoursParked = calculateHoursParked(startTime);
		return calculateAmount(hoursParked);
	}

	public float getTotalAmt() {  // If Start time is missing that means, the driver missed the parking slip
		return calculateAmount(-1);
	}
	
	private int calculateHoursParked(final Calendar startTime) {
		final Calendar currentTime = Calendar.getInstance();
		final long diffInMinutes = (currentTime.getTimeInMillis() - startTime.getTimeInMillis()) / 60000;
		int completedHours = (int) diffInMinutes / 60;
		if ((diffInMinutes % 60) > 0) {  // Add an hours if any extra minutes.
			completedHours ++;
		}
		
		return completedHours;
	}
		
	private float calculateAmount(final int numHours) {
		final Rates rates = new Rates();
		if(numHours == -1 ) {  // -1 is a special case where receipt is missing
			return rates.getReceiptMissingRate();
		}
		final float hourlyRate = rates.getHourlyRate();
		return numHours * hourlyRate;
	}
}
