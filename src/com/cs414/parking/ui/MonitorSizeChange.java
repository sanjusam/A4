package com.cs414.parking.ui;

import java.rmi.RemoteException;

import com.cs414.parking.garageSizeHandler.GarageSizeObserver;

public class MonitorSizeChange
								extends java.rmi.server.UnicastRemoteObject
								implements GarageSizeObserver {
	
	private static int CURRENT_SIZE = 0;
	private static final long serialVersionUID = 1L;

	protected MonitorSizeChange() throws RemoteException {
		super();
	}

	@Override
	public void updateGarageSize(int size) {
		CURRENT_SIZE = size;
	}
	
	public int getCurrentSize() {
		return CURRENT_SIZE;
	}
}
