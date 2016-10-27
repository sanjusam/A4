package com.cs414.parking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction {
	final String transactionFile = "ParkingDetails.txt";
	final String paidTransactionFile = "PaidParkingDetails.txt";
	final List<Receipt> receipts = new ArrayList<>();
	final PaymentGenerator paymentGenerator = new PaymentGenerator();
	Receipt reciptToUpdate = null;
	final Map<String, Integer> perYearlIncome = new HashMap<>();
	final Map<String, Integer> perDayIncome = new HashMap<>();
	final Map<String, Integer> monthlyIncome = new HashMap<>();
	final Map<String, Integer> weeklyIncome = new HashMap<>();

	public String createTransaction(final String vechicleNum) {
		final Receipt receipt = new Receipt(vechicleNum);
		receipts.add(receipt);
		persistTransactionToFile(receipt);
		return receipt.prettyPrint();
	}

	public float getReceiptDetails(final int receiptNum) {
		for(final Receipt recipt : createReceiptsFromSaveTransactions()) {
			if(recipt.getReceiptNum() == receiptNum) {
				float amtToPay = paymentGenerator.getTotalAmt(recipt.getEntryTime());
				String toPrint = recipt.toString().trim() + "\t\t" + Calendar.getInstance().getTime() +"\t\t" + amtToPay + "\n";
				updatedPaymentransactions(toPrint);
				removeTransactionFromFile(Integer.toString(recipt.getReceiptNum()));
				return amtToPay;
			}
		}
		return GarageConstants.AMOUNT_NOT_CALCULATED;
	}
	
	
	public float getAdminOverRideForMissingReceipt () {
		return paymentGenerator.getTotalAmt();
	}
	
	public List<Receipt> getTransactions() {
		return receipts;
	}

	
	
	private List<Receipt> createReceiptsFromSaveTransactions() {
		final List<Receipt> receiptsFromFile = new ArrayList<>();
		final String transFileName =  GarageUtils.getFullPathToResourcesFolder() + File.separator + transactionFile;
		try {
		FileInputStream fstream = new FileInputStream(transFileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)  {
			String [] dataFromFile = strLine.split("\t\t");
			int receiptNum = Integer.parseInt(dataFromFile[0]);
			
			Calendar parkingTime = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			parkingTime.setTime(sdf.parse(dataFromFile[2]));
						
			receiptsFromFile.add(new Receipt(receiptNum, dataFromFile[1], parkingTime));
		}
		br.close();
		} catch(final Exception e) {
			e.getMessage();
		}
		return receiptsFromFile;
	}
	
	
	public Map<String, Integer> getYearlyIncome () {
		return perYearlIncome;
	}
	
	public Map<String, Integer> getMonthlyIncome () {
		return monthlyIncome;
	}
	
	public Map<String, Integer> getWeeklyIncome () {
		return weeklyIncome;
	}
	
	public Map<String, Integer> getDailyIncome () {
		return perDayIncome;
	}
	
	public void generateRevenueNumbers() {
		final String transFileName =  GarageUtils.getFullPathToResourcesFolder() + File.separator + paidTransactionFile;
		perYearlIncome.clear();
		monthlyIncome.clear();
		weeklyIncome.clear();
		perDayIncome.clear();
		
		try {
			FileInputStream fstream = new FileInputStream(transFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)  {
				String [] dataFromFile = strLine.split("\t\t");
				Calendar parkingTime = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
				parkingTime.setTime(sdf.parse(dataFromFile[2]));
				
				float value = Float.parseFloat(dataFromFile[4]);
				
				//Yearly
				String yearString = Integer.toString(parkingTime.get(Calendar.YEAR));
				if(perYearlIncome.get(yearString) == null) {
					perYearlIncome.put(yearString, (int) value);
				} else {
					int curAmt = perYearlIncome.get(yearString).intValue();
					curAmt = (int) (curAmt + value);
					perYearlIncome.put(yearString, (Integer)curAmt);
				}
				
				//Monthly
				SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy MMM");
				String monthParking = monthDateFormat.format(parkingTime.getTime());
				
				if(monthlyIncome.get(monthParking) == null) {
					monthlyIncome.put(monthParking, (int) value);
				} else {
					int curAmt = monthlyIncome.get(monthParking).intValue();
					curAmt = (int) (curAmt + value);
					monthlyIncome.put(monthParking, (Integer)curAmt);
				}
				
				//Weekly
				String week = Integer.toString(parkingTime.get(Calendar.WEEK_OF_YEAR));
				if(weeklyIncome.get(week) == null) {
					weeklyIncome.put(week, (int) value);
				} else {
					int curAmt = weeklyIncome.get(week).intValue();
					curAmt = (int) (curAmt + value);
					weeklyIncome.put(week, (Integer)curAmt);
				}
				
				//Daily
				SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy MMM dd");
				String dayParking = dayDateFormat.format(parkingTime.getTime());
				
				if(perDayIncome.get(dayParking) == null) {
					perDayIncome.put(dayParking, (int) value);
				} else {
					int curAmt = perDayIncome.get(dayParking).intValue();
					curAmt = (int) (curAmt + value);
					perDayIncome.put(dayParking, (Integer)curAmt);
				}
			}
			br.close();
			} catch(final Exception e) {
				e.getMessage();
			}
		
	}

	private void removeTransactionFromFile(final String transactionToRemove) {
		final String transFileName =  GarageUtils.getFullPathToResourcesFolder() + File.separator + transactionFile;
		try {
			File inputFile = new File(transFileName);
			File tempFile = new File(transFileName + ".tmp");
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    if(currentLine.trim().equals(transactionToRemove.trim()) || currentLine.trim().contains(transactionToRemove.trim()) ) continue;
			    writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close(); 
			inputFile.delete();
			tempFile.renameTo(inputFile);
			
		}catch (final Exception e) {
			
		}
	}
	
	private void persistTransactionToFile(final Receipt receipt) {
		GarageUtils.appendToFileInResourceFolder(transactionFile, receipt.toString());
	}
	
	private void updatedPaymentransactions(final String toPrint) {
		GarageUtils.appendToFileInResourceFolder(paidTransactionFile, toPrint);
	}
	
	
}
