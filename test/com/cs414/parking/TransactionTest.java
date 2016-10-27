package com.cs414.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.expert.Receipt;
import com.cs414.parking.expert.TransactionExpert;


public class TransactionTest {
	
	private TransactionExpert transaction ;
	private final String vehicleNum = "abc";
	
	@Before
	public void setup() throws Exception {
		transaction = new TransactionExpert();
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
	
	@Test
	public void testAdminOverideForMissingReceipt() throws Exception {
		final float amt = transaction.getAdminOverRideForMissingReceipt();
		Assert.assertEquals(100, amt, 0.00);
	}
	
	@Test
	public void testGetYearlyRevenue() throws Exception {
		transaction.generateRevenueNumbers();
		Assert.assertNotNull(transaction.getYearlyIncome().get("2016"));
	}
	
	@Test
	public void testGetMonthlyRevenue() throws Exception {
		transaction.generateRevenueNumbers();
		Assert.assertNotNull(transaction.getMonthlyIncome());
	}
	
	@Test
	public void testGetDailyRevenue() throws Exception {
		transaction.generateRevenueNumbers();
		Assert.assertNotNull(transaction.getDailyIncome());
	}
}
