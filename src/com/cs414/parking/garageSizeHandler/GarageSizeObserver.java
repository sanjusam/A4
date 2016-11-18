package com.cs414.parking.garageSizeHandler;

import java.io.Serializable;

public interface GarageSizeObserver extends java.rmi.Remote, Serializable {
	void updateGarageSize(final int size) throws java.rmi.RemoteException;
}
