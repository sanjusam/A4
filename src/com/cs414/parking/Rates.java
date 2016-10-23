package com.cs414.parking;

public class Rates {

	private int hourlyRate = 10;
	private int receiptMissingRate = 100;

	public int getHourlyRate() {
		return hourlyRate;
	}

	public int getReceiptMissingRate() {
		return receiptMissingRate;
	}

	public void setHourlyRate(final int hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public void setReceiptMissingRate(final int receiptMissingRate) {
		this.receiptMissingRate = receiptMissingRate;
	}
}
