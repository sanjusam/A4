import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.Garage;
import com.cs414.parking.GarageConstants;
import com.cs414.parking.GarageUtils;
import com.cs414.parking.Rates;


public class ExitParkingIntergrationTest {

	private final String vechicleNum  = "XAE-121";
	private final Rates rates = new Rates();
	int currentReceiptNum;
	int currentlyOccupied;
	private Garage garage;
	private final int numOfCarsInGarage = 1;
	
		
	@Before
	public void setup() throws Exception {
		garage = new Garage();
		currentReceiptNum = Integer.parseInt(GarageUtils.readOneLineInResourceFolder("ReceiptNumberTracking.txt")) + 1;
		currentlyOccupied = garage.getCurrentOccupancy();
		fillUpParking();
	}
		
	@Test
	public void exitParkingCarOne() throws Exception {
		Assert.assertEquals(numOfCarsInGarage, garage.getCurrentOccupancy());
		
		final String amtToPayMessage = garage.handleExit(Integer.toString(currentReceiptNum));
		Assert.assertEquals("10.0" , amtToPayMessage);
		
		//*Now pay the amount
		garage.makePayment(rates.getHourlyRate());
		Assert.assertEquals(garage.getCurrentOccupancy(), numOfCarsInGarage - 1);
	}
	
	@Test
	public void exitParkingCarInvalidTicket() throws Exception {
		
		final String amtToPayWithInvalidTicket = garage.handleExit("INAVLID_TICKET");
		Assert.assertEquals(GarageConstants.INVALID_RECEIPT_NUM, amtToPayWithInvalidTicket);
	}
	
	@Test
	public void exitParkingCarMissingTicket() throws Exception {
		final String amtToPayWithInvalidTicket = garage.handleMissingTicket();
		Assert.assertEquals("$ 100.0", amtToPayWithInvalidTicket);

		//*Now pay the amount
		garage.makePayment(rates.getReceiptMissingRate());
		Assert.assertEquals(garage.getCurrentOccupancy(), numOfCarsInGarage - 1);
	}
	
	private void fillUpParking() throws InterruptedException {
		for(int car = 0 ; car < numOfCarsInGarage ; car ++ ) {
			garage.handleEntry(vechicleNum + car );
		}
		Thread.sleep(100000);  //Added to induce the parking time
	}
	
}
