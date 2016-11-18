package com.cs414.parking.garageSizeHandler;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GarageSizeTracker extends java.rmi.server.UnicastRemoteObject implements Observable, Serializable {

	private static final long serialVersionUID = 1L;

	private final List<GarageSizeObserver> observers = new ArrayList<>();
	private int garageOccupancy;
	public GarageSizeTracker() throws RemoteException {
		super();
	}
		
	public void setInitialOccupancy(final int garageOccupancy) {
		this.garageOccupancy = garageOccupancy;
	}
	
	public void incrementGarageOccupancy() {
		++garageOccupancy;
		try {
			notifyObservers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void decrementGarageOccupancy() {
		--garageOccupancy;
		try {
			notifyObservers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void registerObserver(final GarageSizeObserver observer) throws java.rmi.RemoteException {
		if(observer != null) {
			observers.add(observer);
		}
	}

	@Override
	public void removeObserver(final GarageSizeObserver observer) throws java.rmi.RemoteException {
		if(observer != null) {
			observers.remove(observers.indexOf(observer));
		}
	}

	@Override
	public void notifyObservers() throws java.rmi.RemoteException {
		for(final GarageSizeObserver observer : observers) {
			observer.updateGarageSize(garageOccupancy);
		}
	}
}
