package cs414.a5.sanjusam.parking;


import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs414.a5.sanjusam.expert.PaymentGenerator;
import cs414.a5.sanjusam.expert.Rates;


public class PaymentGeneratorTest {

	Calendar parkedTimeToBePassed = Calendar.getInstance();
	final Rates rates = new Rates();
	PaymentGenerator generatePayment ;

	@Before
	public void setup() throws Exception {
		generatePayment = new PaymentGenerator();
	}
	
	@Test
	public void testCalculateAmtForTenMinutes() throws Exception {
		//Given
		Date parkedDateTime = new Date();
		parkedDateTime.setMinutes(parkedDateTime.getMinutes() - 10);
		parkedTimeToBePassed.setTime(parkedDateTime);
		
		//When
		float amt = generatePayment.getTotalAmt(parkedTimeToBePassed);
		
		//Then
		Assert.assertEquals(10, amt, 0.000);
		
	}
	
	@Test
	public void testCalculateAmtForOneHourAndOneMinutes() throws Exception {
		//Given
		Date parkedDateTime = new Date();
		parkedDateTime.setMinutes(parkedDateTime.getMinutes() - 71);
		parkedTimeToBePassed.setTime(parkedDateTime);
		
		//When
		float amt = generatePayment.getTotalAmt(parkedTimeToBePassed);
		
		//Then
		Assert.assertEquals(rates.getHourlyRate() * 2,  amt, 0.000);
	}
	
	@Test
	public void testCalculateAmtForOneHourAndFiftyNineMinutes() throws Exception {
		//Given
		Date parkedDateTime = new Date();
		parkedDateTime.setMinutes(parkedDateTime.getMinutes() - 119);
		parkedTimeToBePassed.setTime(parkedDateTime);
		
		//When
		float amt = generatePayment.getTotalAmt(parkedTimeToBePassed);
		
		//Then
		Assert.assertEquals(rates.getHourlyRate() * 2,  amt, 0.000);
	}
	
	@Test
	public void testCalculateAmtWhenSlipMissing() throws Exception {
		float amt = generatePayment.getTotalAmt();
		Assert.assertEquals(rates.getReceiptMissingRate(), amt, 0.000);
	}
}
