package cs414.a5.sanjusam.controller;

import java.rmi.Naming;

public class GarageInitiator {
	
	public GarageInitiator(final String url) {
		try {
			System.out.println("Starting the Garage Application");
			final GarageController garageController = new GarageControllerImpl();
			Naming.rebind(url, garageController);
			System.out.println("Garage Application Started");
		} catch (final Exception e) {
			System.out.println("Error in initilaizing the garage " + e);
			System.exit(-1);
		}
	}
	
	public static void main(String args[]) {
        final String url = "rmi://" + args[0] + ":" + args[1] + "/GarageService";
        new GarageInitiator(url);
    }

}
