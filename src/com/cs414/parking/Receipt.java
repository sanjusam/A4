package com.cs414.parking;

import java.util.Calendar;

public class Receipt {
	private static int RECEIPT_NUM = 1;

	private Calendar entryTime;
	private int receiptNum;
	private final String vechileNum;

	public Receipt(final String vechileNum) {
		this.vechileNum = vechileNum;
		this.entryTime = Calendar.getInstance();
		receiptNum = RECEIPT_NUM++;
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

	@Override
	public String toString() {
		return "Vehicle Number\t\t\t:\t" + vechileNum + "\nReceipt Num\t\t\t:\t" + Integer.toString(receiptNum)
				+ "\nEntry Time\t\t\t:\t" + entryTime.getTime();

	}
}
