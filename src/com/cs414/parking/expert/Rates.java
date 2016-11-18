package com.cs414.parking.expert;

public class Rates {

	private float hourlyRate = 10;
	private float receiptMissingRate = 100;

	public float getHourlyRate() {
		return hourlyRate;
	}

	public float getReceiptMissingRate() {
		return receiptMissingRate;
	}

	public void setHourlyRate(final float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public void setReceiptMissingRate(final float receiptMissingRate) {
		this.receiptMissingRate = receiptMissingRate;
	}
}
