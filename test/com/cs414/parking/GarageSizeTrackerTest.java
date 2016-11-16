package com.cs414.parking;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cs414.parking.garageSizeHandler.GarageSizeTracker;


public class GarageSizeTrackerTest {

	GarageSizeTracker garageSizeTracker;
	GarageSizeTestObserver testObserver;
	final int initialSize = 10;

	@Before
	public void setup() throws Exception {
		testObserver = new GarageSizeTestObserver();
		garageSizeTracker = new GarageSizeTracker();
		garageSizeTracker.setInitialOccupancy(initialSize);
		garageSizeTracker.registerObserver(testObserver);
	}
	
	@Test
	public void testGarageSizeDecrementNotifiyObserver() throws Exception {
		garageSizeTracker.decrementGarageOccupancy();
		Assert.assertEquals(initialSize -1, testObserver.getSize());
	}
	
	@Test
	public void testGarageSizeIncrementNotifiyObserver() throws Exception {
		garageSizeTracker.incrementGarageOccupancy();
		Assert.assertEquals(initialSize +1, testObserver.getSize());
	}

	@Test
	public void testGarageSizeRemoveObserverDoesNotNotify() throws Exception {
		garageSizeTracker.incrementGarageOccupancy();
		Assert.assertEquals(initialSize +1, testObserver.getSize());
		
		garageSizeTracker.removeObserver(testObserver);
		garageSizeTracker.incrementGarageOccupancy();
		Assert.assertEquals(initialSize +1, testObserver.getSize());
	}
}
