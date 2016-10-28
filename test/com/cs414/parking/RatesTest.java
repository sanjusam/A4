package com.cs414.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.expert.Rates;

public class RatesTest {
	
	private Rates parkingRates;
	
	@Before	
	public void setup() throws Exception {
		parkingRates = new Rates();
	}

	@Test
	public void testHourlyRate() throws Exception {
		Assert.assertEquals(10, parkingRates.getHourlyRate(), 0.000);
	}
	
	@Test
	public void testMissingSlipRate() throws Exception {
		Assert.assertEquals(100, parkingRates.getReceiptMissingRate(), 0.000);
	}
	
	@Test
	public void testSetRatesMissingRates() throws Exception {
		Assert.assertEquals(100, parkingRates.getReceiptMissingRate(), 0.000);
		parkingRates.setReceiptMissingRate(500);
		Assert.assertEquals(500, parkingRates.getReceiptMissingRate(), 0.000);
	}
	
	@Test
	public void testSetRatesHourlyRates() throws Exception {
		Assert.assertEquals(10, parkingRates.getHourlyRate(), 0.000);
		parkingRates.setHourlyRate(300);
		Assert.assertEquals(300, parkingRates.getHourlyRate(), 0.000);
	}

}
