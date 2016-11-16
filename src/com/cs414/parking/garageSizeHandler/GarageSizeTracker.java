package com.cs414.parking.garageSizeHandler;

import java.util.ArrayList;
import java.util.List;

public class GarageSizeTracker implements Observable {

	private final List<GarageSizeObserver> observers = new ArrayList<>();
	private int garageOccupancy;
		
	public void setInitialOccupancy(final int garageOccupancy) {
		this.garageOccupancy = garageOccupancy;
	}
	
	public void incrementGarageOccupancy() {
		++garageOccupancy;
		notifyObservers();
	}

	public void decrementGarageOccupancy() {
		--garageOccupancy;
		notifyObservers();
	}
	
	@Override
	public void registerObserver(final GarageSizeObserver observer) {
		if(observer != null) {
			observers.add(observer);
		}
	}

	@Override
	public void removeObserver(final GarageSizeObserver observer) {
		if(observer != null) {
			observers.remove(observers.indexOf(observer));
		}
	}

	@Override
	public void notifyObservers() {
		for(final GarageSizeObserver observer : observers) {
			observer.updateGarageSize(garageOccupancy);
		}
	}
}
