package com.cs414.parking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Transaction {
	final String transactionFile = "ParkingDetails.txt";
	final String paidTransactionFile = "PaidParkingDetails.txt";
	final List<Receipt> receipts = new ArrayList<>();
	final PaymentGenerator paymentGenerator = new PaymentGenerator();
	Receipt reciptToUpdate = null;

	public String createTransaction(final String vechicleNum) {
		final Receipt receipt = new Receipt(vechicleNum);
		receipts.add(receipt);
		persistTransactionToFile(receipt);
		return receipt.prettyPrint();
	}

	public float getReceiptDetails(final int receiptNum) {
		for(final Receipt recipt : receipts) {
			if(recipt.getReceiptNum() == receiptNum) {
				float amtToPay = paymentGenerator.getTotalAmt(recipt.getEntryTime());
				String toPrint = recipt.toString() + "\t" + Calendar.getInstance().getTime() +"\t" + amtToPay;
				updatedPaymentransactions(toPrint);
				return amtToPay;
			}
		}
		return GarageConstants.AMOUNT_NOT_CALCULATED;
	}
	
	public float getAdminOverRideForMissingReceipt () {
		return paymentGenerator.getTotalAmt();
	}
	
	public List<Receipt> getTransactions() {
		return receipts;
	}

	private void persistTransactionToFile(final Receipt receipt) {
		GarageUtils.appendToFileInResourceFolder(transactionFile, receipt.toString());
	}
	
	private void updatedPaymentransactions(final String toPrint) {
		GarageUtils.appendToFileInResourceFolder(paidTransactionFile, toPrint);
	}
}
