package cs414.a5.sanjusam.parking;

import org.junit.Assert;
import org.junit.Test;

import cs414.a5.sanjusam.utils.GarageConstants;


public class GarageConstantsTest {

	@Test
	public void tesHeaderParkingNotAvaiable() throws Exception {
		Assert.assertEquals("\t\t\tSORRY PARKING FULL", GarageConstants.MSG_PARKING_NOT_AVAILABLE);
	}
	
	@Test
	public void testHeaderParkingSlipHeader() throws Exception {
		Assert.assertEquals("\t\t\tCity of Fort Collins" + "\n" +
				"\t\t\t====================" + "\n", GarageConstants.MSG_PARKING_SLIP_HEADER);
	}
	
	@Test
	public void testInvalidTicketEntry() throws Exception {
		Assert.assertEquals("Invalid Receipt - Re-enter", GarageConstants.INVALID_RECEIPT_NUM);
	}
	
		
	@Test
	public void testAmountCoundNotCalculate() throws Exception {
		Assert.assertEquals(-1, GarageConstants.AMOUNT_NOT_CALCULATED, 0.0);
	}
	
	@Test
	public void testHanldeMissingSlip() throws Exception {
		Assert.assertEquals("WARNING !!! " + "\n"  + "Missing parking ticket would invite huge fines!!!", GarageConstants.MISSING_LOST_RECEIPT_WARNING);
	}
	
	
	@Test
	public void testExitGarageSuccessully() throws Exception {
		Assert.assertEquals("You exit the Garage, Have a good Day", GarageConstants.EXIT_GARAGE_SUCCESSFULY);
	}
	
	@Test
	public void testExitGarageFailure() throws Exception {
		Assert.assertEquals("Not Successful yet!", GarageConstants.EXIT_GARAGE_NOT_ALLOWED);
	}

}
