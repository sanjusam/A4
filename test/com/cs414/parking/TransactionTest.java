package com.cs414.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TransactionTest {
	
	private Transaction transaction ;
	private final String vehicleNum = "abc";
	
	@Before
	public void setup() throws Exception {
		transaction = new Transaction();
	}

	@Test
	public void testDefaultTransactions() throws Exception {
		Assert.assertEquals(0, transaction.getTransactions().size());
	}
	
	@Test
	public void testCreateTransactionUpdatesTransactionList() throws Exception {
		Assert.assertEquals(0, transaction.getTransactions().size());
		transaction.createTransaction(vehicleNum);
		Assert.assertEquals(1, transaction.getTransactions().size());
	}
	
	@Test
	public void testCreateTransactionUpdatesTransaactions() throws Exception {
		transaction.createTransaction(vehicleNum);
		final Receipt receipt = transaction.getTransactions().get(0);
		
		Assert.assertEquals(vehicleNum, receipt.getVechicleNum());
		Assert.assertTrue(receipt.toString().contains(vehicleNum));
	}

	
	@Test
	public void testCreateTransactionContainsValidInfo() throws Exception {
		transaction.createTransaction(vehicleNum);
		final Receipt receipt = transaction.getTransactions().get(0);
		
		Assert.assertNotNull(receipt);
		Assert.assertNotNull(receipt.getVechicleNum());
		Assert.assertNotNull(receipt.getEntryTime());
		Assert.assertNotNull(receipt.getReceiptNum());
	}
	
	@Test
	public void testCreateTransactionReturnsValidReceipt() throws Exception {
		final String receipt = transaction.createTransaction(vehicleNum);
				
		Assert.assertNotNull(receipt);
		Assert.assertTrue(receipt.contains(vehicleNum));
	}

}
