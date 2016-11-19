package cs414.a5.sanjusam.garageSizeHandler;

import java.io.Serializable;

public interface GarageSizeObserver extends java.rmi.Remote, Serializable {
	void updateGarageSize(final int size) throws java.rmi.RemoteException;
}
