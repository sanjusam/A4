package cs414.a5.sanjusam.controller;

import java.io.Serializable;

import cs414.a5.sanjusam.garageSizeHandler.GarageSizeTracker;
import cs414.a5.sanjusam.garageSizeHandler.Observable;

public interface GarageController extends java.rmi.Remote, Serializable {
	public String handleEntry(final String vechicleNum) throws java.rmi.RemoteException;
	public String handleExit(final String receiptNum) throws java.rmi.RemoteException;
	public String handleMissingTicket() throws java.rmi.RemoteException; 
	public String makePayment(float amout) throws java.rmi.RemoteException; 
	public String updateParkingGarageSize(final int newSize) throws java.rmi.RemoteException;
	public String handleFinancialReporting() throws java.rmi.RemoteException ;
	
	public Observable getGarageSizeTracker() throws java.rmi.RemoteException;
	public int getCurrentOccupancy() throws java.rmi.RemoteException;
	public int getCapacity() throws java.rmi.RemoteException;
}
