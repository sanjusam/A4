package com.cs414.parking;

import java.util.Calendar;

public class Receipt {
	ReceiptNumberGenerator receiptNumGenerator = new ReceiptNumberGenerator();

	private Calendar entryTime;
	private int receiptNum;
	private final String vechileNum;

	public Receipt(final String vechileNum) {
		this.vechileNum = vechileNum;
		this.entryTime = Calendar.getInstance();
		receiptNum = receiptNumGenerator.getReceiptNum();
	}

	public Calendar getEntryTime() {
		return entryTime;
	}

	public int getReceiptNum() {
		return receiptNum;
	}

	public String getVechicleNum() {
		return vechileNum;
	}

	public String prettyPrint() {
		return "Vehicle Number\t\t\t:\t" + vechileNum + "\nReceipt Num\t\t\t:\t" + Integer.toString(receiptNum)
				+ "\nEntry Time\t\t\t:\t" + entryTime.getTime();

	}
	
	@Override
	public String toString() {
		return receiptNum + "\t\t" + vechileNum + "\t\t" + entryTime.getTime();
	}
	
	
}
