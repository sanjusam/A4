import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.controller.GarageController;
import com.cs414.parking.utils.GarageConstants;
import com.cs414.parking.utils.GarageUtils;

public class ParkingIntergrationTest {
	
	private final String vechicleNum  = "XAE-121";
	int currentReceiptNum;
	int currentlyOccupied;
	private GarageController garage;
	
		
	@Before
	public void setup() throws Exception {
		garage = new GarageController();
		currentReceiptNum = Integer.parseInt(GarageUtils.readOneLineInResourceFolder("ReceiptNumberTracking.txt")) + 1;
		currentlyOccupied = garage.getCurrentOccupancy();
	}
	
	@After
	public void tearDownGarage() { 
		GarageUtils.writeToFileInResourceFolder("ParkingDetails.txt", "");
		garage.setOccupied(0);
	}
	
	@Test
	public void testParkCarInGarageUpdatedGarageAndGivesValidReceiptNum() throws Exception {
		//*When
		final String parkingSlip = garage.handleEntry(vechicleNum);
		
		//*Then 
		Assert.assertEquals(currentlyOccupied + 1 , garage.getCurrentOccupancy());
		Assert.assertNotNull(parkingSlip);
		Assert.assertTrue(parkingSlip.contains(vechicleNum));
		Assert.assertTrue(parkingSlip.contains(Integer.toString(currentReceiptNum)));
	}
	
	@Test
	public void testParkSecondCarUpdatesOccupancy() throws Exception {
		//*When
		garage.handleEntry(vechicleNum);
		
		//*Then 
		Assert.assertEquals(currentlyOccupied + 1 , garage.getCurrentOccupancy());
	}
	
	@Test
	public void testParkFillupParkingAndLastCarGetsRejected() throws Exception {
		final int numCarsCanPark = garage.getCapacity() - garage.getCurrentOccupancy();
		
		//**Given -- fill up the garage
		for(int car = 0 ; car < numCarsCanPark ; car ++ ) {
			garage.handleEntry(vechicleNum + car );
		}
		Assert.assertEquals(garage.getCapacity(), garage.getCurrentOccupancy());  // Make sure  the Garage is full
		
		//**Then -- Assert that the parking is denied
		Assert.assertEquals(GarageConstants.MSG_PARKING_SLIP_HEADER + GarageConstants.MSG_PARKING_NOT_AVAILABLE, 
				garage.handleEntry(vechicleNum + 999));
		
		//No change in the garage 
		Assert.assertEquals(garage.getCapacity(), garage.getCurrentOccupancy());  // Make sure  the Garage is full
	}

}
