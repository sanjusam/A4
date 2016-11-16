package com.cs414.parking.garageSizeHandler;

public interface Observable {
	void registerObserver(final GarageSizeObserver observer);
    void removeObserver(final GarageSizeObserver observer);
    void notifyObservers();
}
