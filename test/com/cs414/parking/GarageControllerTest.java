package com.cs414.parking;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.controller.GarageController;
import com.cs414.parking.expert.Transaction;
import com.cs414.parking.utils.GarageConstants;
import com.cs414.parking.utils.GarageUtils;


public class GarageControllerTest {
	
	private GarageController parkingGarage;
	private final String vehicleNum = "112-RYA";
	private final String capacityConfigurationFile = "GarageCapacity.txt";
	private int originalCapacity;
	private int capacity ;
	private Transaction transactionMock;
	final String transactioFileForTest = "ParkingDetailsForTest.txt";
	final String paidTransactioFileForTest = "PaidParkingDetailsForTest.txt";
	

	@Before
	public void setup() throws Exception {
		parkingGarage = new GarageController();
		capacity = parkingGarage.getCapacity();
		originalCapacity = parkingGarage.readCapacityFromConfiguration();
		transactionMock = new Transaction();
		transactionMock.setTransactionFile(transactioFileForTest);
		transactionMock.setPaidTransactionFile(paidTransactioFileForTest);
		updateTestFile();
	}
	
	@After
	public void tearDown() {
		GarageUtils.updateSingleValueInResourceFolder(capacityConfigurationFile, originalCapacity);
	}
	
	
	@Test
	public void testCapacity() throws Exception {
		Assert.assertEquals(capacity, parkingGarage.getCapacity());
	}
	
	@Test
	public void testInitialAvailablity() throws Exception {
		Assert.assertTrue(parkingGarage.isAvailable());
	}
	
	@Test
	public void testAvailbalityIfOneLess() throws Exception {
		parkingGarage.setOccupied(capacity - 1);
		Assert.assertTrue(parkingGarage.isAvailable());
		
	}
	
	@Test
	public void testAvailbalityIfAllused() throws Exception {
		parkingGarage.setOccupied(capacity);
		Assert.assertFalse(parkingGarage.isAvailable());
	}
	
	@Test
	public void testParkVechileSuccessfullyReturnsReceipt() throws Exception {
		final String receipt = parkingGarage.handleEntry(vehicleNum);
		Assert.assertTrue(!receipt.isEmpty());
		Assert.assertTrue(receipt.contains(vehicleNum));
		Assert.assertTrue(receipt.contains(GarageConstants.MSG_PARKING_SLIP_HEADER));
		Assert.assertTrue(!receipt.contains(GarageConstants.MSG_PARKING_NOT_AVAILABLE));
		System.out.println(receipt);
	}
	
	@Test
	public void testParkingDeniedWhenGarageFull() throws Exception {
		parkingGarage.setOccupied(capacity);
		
		final String receipt = parkingGarage.handleEntry(vehicleNum);
		Assert.assertTrue(!receipt.isEmpty());
		Assert.assertTrue(!receipt.contains(vehicleNum));
		Assert.assertTrue(receipt.contains(GarageConstants.MSG_PARKING_SLIP_HEADER));
		Assert.assertTrue(receipt.contains(GarageConstants.MSG_PARKING_NOT_AVAILABLE));
		Assert.assertEquals(capacity, parkingGarage.getCapacity());
		System.out.println(receipt);
	}
	
	@Test
	public void testUpdatedCapacityReturnsMessage() throws Exception {
		//*Given
		final int newCapacity = 5;
		final String message = parkingGarage.updateParkingGarageSize(newCapacity);
		Assert.assertEquals("Garage upgraded to "+ newCapacity, message);
	}
		
			
	@Test
	public void testUpdatedCapacity() throws Exception {
		//*Given
		final int newCapacity = 5;
		parkingGarage.updateParkingGarageSize(newCapacity);
		
		//*When
		final int capacity = parkingGarage.readCapacityFromConfiguration();
		
		//*Then
		Assert.assertEquals(newCapacity, capacity);
	}
	
	@Test
	public void testGetCapacity() throws Exception {
		parkingGarage.intejectTransactionForTest(transactionMock);
		Assert.assertEquals(2, parkingGarage.getCurrentOccupancy());
	}
	
	@Test
	public void testhandleMissingTicket() throws Exception {
		Assert.assertEquals("100.0", parkingGarage.handleMissingTicket());
		
	}
	
	@Test
	public void testHandleMakePaymentError() throws Exception {
		Assert.assertEquals("Not Successful yet!", parkingGarage.makePayment(100));
	}
	
	@Test
	public void testHandleMakePayment() throws Exception {
		parkingGarage.injectPaymentDueForTest(80);
				
		Assert.assertEquals("You exit the Garage, Have a good Day", 
				parkingGarage.makePayment(80));
	}
	
	@Test
	public void testGetMostUsedDay() throws Exception {
		parkingGarage.intejectTransactionForTest(transactionMock);
		
		Assert.assertNotNull("2016 Oct 22 Usage is : 230", parkingGarage.getMostUsedDay());
	}

	private void updateTestFile() {
		final String textToWrite = "1001		ABC-123		Wed Oct 26 19:27:34 MDT 2016" + "\n" +
									"1002		ABC-234		Wed Oct 26 19:27:34 MDT 2016" + "\n";
		GarageUtils.writeToFileInResourceFolder(transactioFileForTest, textToWrite);
		final String amtTxt =  "1001		ABC-123		Wed Oct 22 19:27:34 MDT 2016		Thu Oct 27 18:23:07 MDT 2016		230.0\n" +
								"1002		ABC-345		Wed Oct 24 19:27:34 MDT 2016		Thu Oct 27 18:23:07 MDT 2016		10.0\n";
		GarageUtils.writeToFileInResourceFolder(paidTransactioFileForTest, amtTxt);
	}
}
