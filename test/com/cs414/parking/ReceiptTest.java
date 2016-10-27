package com.cs414.parking;

import java.util.Calendar;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.expert.Receipt;

public class ReceiptTest {
	
	private final String vechicleNum = "abc";
	private Calendar testBeginTime;
	private Receipt receipt;
	
	@Before
	public void setup() throws Exception {
		testBeginTime = Calendar.getInstance();
		Thread.sleep(100);
		receipt = new Receipt(vechicleNum);
	}
	
	@Test
	public void testReceiptObjectVechicleNum() throws Exception {
		Assert.assertEquals(vechicleNum, receipt.getVechicleNum());
	}
	@Test
	public void testReceiptNumNotNull() throws Exception {
		Assert.assertNotNull(receipt.getReceiptNum());
	}
	
	@Test 
	public void testEntryTime() throws Exception {
		Assert.assertTrue(receipt.getEntryTime() instanceof Calendar);
	}
	
	
	@Test
	public void testEntryTimeValidation() throws Exception {
		Thread.sleep(100);
		final Calendar timeAfterReceiptCreation = Calendar.getInstance();
		Assert.assertTrue(timeAfterReceiptCreation.getTimeInMillis() > receipt.getEntryTime().getTimeInMillis());
		Assert.assertTrue(testBeginTime.getTimeInMillis() < receipt.getEntryTime().getTimeInMillis());
	}
	}
