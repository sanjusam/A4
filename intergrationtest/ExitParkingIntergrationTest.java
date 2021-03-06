import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cs414.a5.sanjusam.controller.GarageControllerImpl;
import cs414.a5.sanjusam.expert.Rates;
import cs414.a5.sanjusam.utils.GarageConstants;
import cs414.a5.sanjusam.utils.GarageUtils;


public class ExitParkingIntergrationTest {

	private final String vechicleNum  = "XAE-121";
	private final Rates rates = new Rates();
	int currentReceiptNum;
	int currentlyOccupied;
	private GarageControllerImpl garage;
	private final int numOfCarsInGarage = 1;
	
	@Before
	public void setup() throws Exception {
		garage = new GarageControllerImpl();
		GarageUtils.writeToFileInResourceFolder("ParkingDetails.txt", "");
		currentReceiptNum = Integer.parseInt(GarageUtils.readOneLineInResourceFolder("ReceiptNumberTracking.txt")) + 1;
		currentlyOccupied = garage.getCurrentOccupancy();
		fillUpParking();
	}
	
	@After
	public void tearDownGarage() throws Exception {
		GarageUtils.writeToFileInResourceFolder("ParkingDetails.txt", "");
	}
	
	@Test
	public void exitParkingCarOne() throws Exception {
				
		final String amtToPayMessage = garage.handleExit(Integer.toString(currentReceiptNum));
		Assert.assertEquals("10.0" , amtToPayMessage);
		
		//*Now pay the amount
		String msg = garage.makePayment(rates.getHourlyRate());
		Assert.assertEquals(GarageConstants.EXIT_GARAGE_SUCCESSFULY, msg);
	}
	
	@Test
	public void exitParkingCarInvalidTicket() throws Exception {
		
		final String amtToPayWithInvalidTicket = garage.handleExit("INAVLID_TICKET");
		Assert.assertEquals(GarageConstants.INVALID_RECEIPT_NUM, amtToPayWithInvalidTicket);
	}
	
	@Test
	public void exitParkingCarMissingTicket() throws Exception {
		final String amtToPayWithInvalidTicket = garage.handleMissingTicket();
		Assert.assertEquals("100.0", amtToPayWithInvalidTicket);

		//*Now pay the amount
		garage.makePayment(rates.getReceiptMissingRate());
		Assert.assertEquals(garage.getCurrentOccupancy(), numOfCarsInGarage - 1);
	}
	
	private void fillUpParking() throws Exception {
		for(int car = 0 ; car < numOfCarsInGarage ; car ++ ) {
			garage.handleEntry(vechicleNum + car );
		}
//		Thread.sleep(100000);  //Added to induce the parking time
	}
	
}
