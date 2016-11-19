package cs414.a5.sanjusam.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs414.a5.sanjusam.utils.GarageUtils;
import cs414.a5.sanjusam.utils.ReceiptNumberGenerator;


public class ReceiptNumberGeneratorTest {
	
	final String receiptNumFile = "ReceiptNumberTracking.txt";
	final ReceiptNumberGenerator receiptNumGenerator = new ReceiptNumberGenerator();
	int curReceipt ;
	
	
	@Before
	public void setup() throws Exception {
		curReceipt = Integer.parseInt(GarageUtils.readOneLineInResourceFolder(receiptNumFile));
		
	}
	
	@Test
	public void generateReceiptNumIncrementsByOneFileContent() throws Exception {
		Assert.assertEquals(curReceipt + 1, receiptNumGenerator.getReceiptNum());
	}
	
	@Test
	public void receiptFileGetIncrementedByOneAfterReceiptGeneration() throws Exception {
		int newValueInfile = curReceipt + 1;
		
		Assert.assertEquals(curReceipt + 1, receiptNumGenerator.getReceiptNum());
		
		Assert.assertEquals(Integer.toString(newValueInfile), GarageUtils.readOneLineInResourceFolder(receiptNumFile));
	}

}
