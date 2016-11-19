package cs414.a5.sanjusam.parking;

import cs414.a5.sanjusam.garageSizeHandler.GarageSizeObserver;

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
