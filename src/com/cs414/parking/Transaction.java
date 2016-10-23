package com.cs414.parking;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	final List<Receipt> receipts = new ArrayList<>();

	public String createTransaction(final String vechicleNum) {
		final Receipt receipt = new Receipt(vechicleNum);
		receipts.add(receipt);
		return receipt.toString();
	}

	public List<Receipt> getTransactions() {
		return receipts;
	}

}
