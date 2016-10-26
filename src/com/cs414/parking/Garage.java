package com.cs414.parking;

import java.io.File;
import java.util.Map;

public class Garage {
	final String capacityFile= "GarageCapacity.txt";
	private int CAPACITY = readCapacityFromConfiguration();
	private int currentOccupancy;
	final Transaction transaction = new Transaction();
	float amt = GarageConstants.AMOUNT_NOT_CALCULATED;

	public int getCapacity() {
		return CAPACITY;
	}

	public int getCurrentOccupancy() {
		return currentOccupancy;
	}

	public String handleEntry(final String vechicleNum) {

		if(isAvailable()) {
			currentOccupancy ++;
			return GarageConstants.MSG_PARKING_SLIP_HEADER + transaction.createTransaction(vechicleNum);
		} else {
			return GarageConstants.MSG_PARKING_SLIP_HEADER + GarageConstants.MSG_PARKING_NOT_AVAILABLE;
		}
	}

	public String handleExit(final String receiptNum) {
		try {
			final int receiptNumValid  = Integer.parseInt(receiptNum);
			amt = transaction.getReceiptDetails(receiptNumValid);
			if(amt == GarageConstants.AMOUNT_NOT_CALCULATED) {
				return GarageConstants.INVALID_RECEIPT_NUM;
			}
			return Float.toString(amt);
		} catch (final NumberFormatException nfe) {
			return GarageConstants.INVALID_RECEIPT_NUM;
		}
	}
	
	public String makePayment(float amout) {
		if ((amt != GarageConstants.AMOUNT_NOT_CALCULATED) && (amout == amt))  {
			if(currentOccupancy > 0) { 
				currentOccupancy --;
			}
			amt = GarageConstants.AMOUNT_NOT_CALCULATED;
			return GarageConstants.EXIT_GARAGE_SUCCESSFULY;
		} else {
			return GarageConstants.EXIT_GARAGE_NOT_ALLOWED;
		}
	}
	
	public String handleMissingTicket() {
		amt = transaction.getAdminOverRideForMissingReceipt();
		return Float.toString(amt);
	}
	
	public String handleFinancialReporting() {
		final String reportFileName = System.getProperty("java.io.tmpdir") + File.separator + "GarageReports.txt";
		final String deliminter = "\n=================================================================\n"; 
		String reportDetails = "";
		reportDetails += handleFinancialReportingDetails();
		reportDetails += deliminter;
		reportDetails += getMostUsedDay();
		reportDetails += deliminter ;
		reportDetails += getMostUsedMonth();
		reportDetails += deliminter ;
		reportDetails += "Occupancy :" +  currentOccupancy + "/" + CAPACITY;
		
		GarageUtils.writeToFileInResourceFolder(reportFileName, reportDetails);
		
		return "Output Generated to : " + reportFileName;
	}
	
	public String handleFinancialReportingDetails() {
		transaction.generateRevenueNumbers();
		String revenueString = "\nYEARLY REVENUE FIGURES\n=================================\n" ;
		Map <String, Integer> yearlyRevenue = transaction.getYearlyIncome();
		for(final String year : yearlyRevenue.keySet()) {
			revenueString += "\n" + year +"	-> $" + yearlyRevenue.get(year) + "\n"; 
		}
		
		revenueString += "\nMONTHY REVENUE FIGURES\n=================================\n" ;
		Map <String, Integer> monthly = transaction.getMonthlyIncome();
		for(final String month : monthly.keySet()) {
			revenueString += "\n" + month +"	-> $" + monthly.get(month) + "\n"; 
		}
		
		revenueString += "\nWEEKLY REVENUE FIGURES\n=================================\n" ;
		Map <String, Integer> weekly = transaction.getWeeklyIncome();
		for(final String week : weekly.keySet()) {
			revenueString += "\n" + week +"	-> $" + weekly.get(week) + "\n\n"; 
		}
		
		revenueString += "\nDAILY REVENUE FIGURES\n=================================\n" ;
		Map <String, Integer> daily = transaction.getDailyIncome();
		for(final String day : daily.keySet()) {
			revenueString += "\n" + day +"	-> $" + daily.get(day) + "\n\n";
		}
		
		return revenueString;
	}

	public String getMostUsedMonth() {
		transaction.generateRevenueNumbers();
		String highestUsedMonth = "";
		Integer usage  = 0;
		Map <String, Integer> monthly = transaction.getMonthlyIncome();
		for(final String month : monthly.keySet()) {
			if(usage < monthly.get(month)) {
				usage = monthly.get(month);
				highestUsedMonth = month;
			}
		}
		
		return "The highest used month is  : " + highestUsedMonth + " Usage is : " + usage;
	}
	
	public String getMostUsedDay() {
		transaction.generateRevenueNumbers();
		String highestUsedDay = "";
		Integer usage  = 0;
		Map <String, Integer> daily = transaction.getDailyIncome();
		for(final String day : daily.keySet()) {
			if(usage < daily.get(day)) {
				usage = daily.get(day);
				highestUsedDay = day;
			}
		}
		
		return "The highest used day is  : " + highestUsedDay + " Usage is : " + usage;
	}
	
	public boolean isAvailable() {
		return currentOccupancy < CAPACITY;
	}
	
	public String updateParkingGarageSize(final int newSize) {
		GarageUtils.updateSingleValueInResourceFolder(capacityFile, newSize);
		CAPACITY = newSize;
		return "Garage upgraded to " + newSize;
	}
	
	public int readCapacityFromConfiguration() {
		final int defaultParkingSize = 5;
		try {
			return Integer.parseInt(GarageUtils.readOneLineInResourceFolder(capacityFile));
		} catch(final NumberFormatException nfe) {
			//Default parking size.
			return defaultParkingSize;
		}
	}

	// @VisibleForTesting  -- Wanted  to use this annotation, but not sure if its available on the lab machines
		//Testing support
		public void setOccupied(final int occupied) {
			this.currentOccupancy = occupied;
		}
		
}
