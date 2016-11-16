package com.cs414.parking;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.expert.Receipt;
import com.cs414.parking.expert.Transaction;
import com.cs414.parking.utils.GarageConstants;
import com.cs414.parking.utils.GarageUtils;


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
	
	@Test
	public void testGetReceiptDetails() throws Exception {
		updateTestFile();
		transaction.setTransactionFile("ParkingDetailsForTest.txt");
		float amnt = transaction.getReceiptDetails(1001);
		Assert.assertTrue(amnt > 10.0);
	}
	
	@Test
	public void testGetReceiptDetailsForNonExistingReceipt() throws Exception {
		updateTestFile();
		transaction.setTransactionFile("ParkingDetailsForTest.txt");
		float amnt = transaction.getReceiptDetails(787878);
		Assert.assertEquals(amnt, GarageConstants.AMOUNT_NOT_CALCULATED, 0.0);
	}
	
	@Test
	public void testUpdatePayment() throws Exception {
		updateTestFile();
		final String transactioFileForTest = "ParkingDetailsForTest.txt";
		final String paidTransactioFileForTest = "PaidParkingDetailsForTest.txt";
		final File paidTransaction = new File(GarageUtils.getFullPathToResourcesFolder() + paidTransactioFileForTest);
		if(paidTransaction.exists()) {
			paidTransaction.delete();
		}
		
		transaction.setTransactionFile(transactioFileForTest);
		transaction.setPaidTransactionFile(paidTransactioFileForTest);
		
		Assert.assertEquals(2, GarageUtils.getLineCountInFile(transactioFileForTest, true));
		
		transaction.getReceiptDetails(1001);
		transaction.updateTransactionRecords();
		Assert.assertEquals(1, GarageUtils.getLineCountInFile(transactioFileForTest, true));
		Assert.assertEquals(1, GarageUtils.getLineCountInFile(paidTransactioFileForTest, true));
	
	}
	
	@Test
	public void testGetNumVechicles() throws Exception {
		updateTestFile();
		final String transactioFileForTest = "ParkingDetailsForTest.txt";
		transaction.setTransactionFile(transactioFileForTest);
		Assert.assertEquals(2, transaction.getNumberOfVehicles());
	}
	
	private void updateTestFile() {
		final String textToWrite = "1001		ABC-123		Wed Oct 26 19:27:34 MDT 2016" + "\n" +
									"1002		ABC-234		Wed Oct 26 19:27:34 MDT 2016" + "\n";
		GarageUtils.writeToFileInResourceFolder("ParkingDetailsForTest.txt", textToWrite);
	}

}
