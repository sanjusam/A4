package com.cs414.parking;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GarageTest {
	
	private Garage parkingGarage;
	private final String vehicleNum = "112-RYA";
	private final int capacity = 100;

	@Before
	public void setup() throws Exception {
		parkingGarage = new Garage();
	}
	
	@Test
	public void testCapacity() throws Exception {
		Assert.assertEquals(capacity, parkingGarage.getCapacity());
	}
	
	@Test
	public void testInitialAvailablity() throws Exception {
		Assert.assertTrue(parkingGarage.parkingAvailable());
	}
	
	@Test
	public void testAvailbalityIfOneLess() throws Exception {
		parkingGarage.setOccupied(capacity - 1);
		Assert.assertTrue(parkingGarage.parkingAvailable());
		
	}
	
	@Test
	public void testAvailbalityIfAllused() throws Exception {
		parkingGarage.setOccupied(capacity);
		Assert.assertFalse(parkingGarage.parkingAvailable());
	}
	
	@Test
	public void testParkVechileSuccessfullyReturnsReceipt() throws Exception {
		final String receipt = parkingGarage.handleEntry(vehicleNum);
		Assert.assertTrue(!receipt.isEmpty());
		Assert.assertTrue(receipt.contains(vehicleNum));
		Assert.assertTrue(receipt.contains(Garage.MSG_PARKING_SLIP_HEADER));
		Assert.assertTrue(!receipt.contains(Garage.MSG_PARKING_NOT_AVAILABLE));
		System.out.println(receipt);
	}
	
	@Test
	public void testParkingDeniedWhenGarageFull() throws Exception {
		parkingGarage.setOccupied(capacity);
		
		final String receipt = parkingGarage.handleEntry(vehicleNum);
		Assert.assertTrue(!receipt.isEmpty());
		Assert.assertTrue(!receipt.contains(vehicleNum));
		Assert.assertTrue(receipt.contains(Garage.MSG_PARKING_SLIP_HEADER));
		Assert.assertTrue(receipt.contains(Garage.MSG_PARKING_NOT_AVAILABLE));
		Assert.assertEquals(capacity, parkingGarage.getCapacity());
		System.out.println(receipt);
	}
}
