package com.cs414.parking;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.controller.GarageController;
import com.cs414.parking.utils.GarageConstants;
import com.cs414.parking.utils.GarageUtils;


public class GarageTest {
	
	private GarageController parkingGarage;
	private final String vehicleNum = "112-RYA";
	private final String capacityConfigurationFile = "GarageCapacity.txt";
	private int originalCapacity;
	private int capacity ;

	@Before
	public void setup() throws Exception {
		parkingGarage = new GarageController();
		capacity = parkingGarage.getCapacity();
		originalCapacity = parkingGarage.readCapacityFromConfiguration();
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
}
