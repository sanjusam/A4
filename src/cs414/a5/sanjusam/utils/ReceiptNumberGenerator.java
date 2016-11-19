package cs414.a5.sanjusam.utils;

public class ReceiptNumberGenerator {
	
	private static String RECEIPT_NUM_FILE = "ReceiptNumberTracking.txt";
		
	public synchronized int getReceiptNum() {
		String receiptNum = GarageUtils.readOneLineInResourceFolder(RECEIPT_NUM_FILE);
		int curReceiptNum =  Integer.parseInt(receiptNum);
		curReceiptNum ++;
		GarageUtils.updateSingleValueInResourceFolder(RECEIPT_NUM_FILE, curReceiptNum);
		return curReceiptNum;
	}
}
