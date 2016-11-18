package com.cs414.parking.garageSizeHandler;

import java.io.Serializable;

public interface Observable extends java.rmi.Remote, Serializable {
	void registerObserver(final GarageSizeObserver observer) throws java.rmi.RemoteException;
    void removeObserver(final GarageSizeObserver observer) throws java.rmi.RemoteException;
    void notifyObservers() throws java.rmi.RemoteException;
}
