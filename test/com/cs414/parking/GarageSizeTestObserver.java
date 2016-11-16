package com.cs414.parking;

import com.cs414.parking.garageSizeHandler.GarageSizeObserver;

public class GarageSizeTestObserver implements GarageSizeObserver {

	private int curSize;
	
	@Override
	public void updateGarageSize(int size) {
		curSize = size;
	}
	
	public int getSize() {
		return curSize;
	}
}
