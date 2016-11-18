package com.cs414.parking.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.cs414.parking.controller.GarageController;
import com.cs414.parking.garageSizeHandler.GarageSizeObserver;
import com.cs414.parking.garageSizeHandler.Observable;
import com.cs414.parking.utils.GarageConstants;

public class HandleVehicleEntry 
					extends java.rmi.server.UnicastRemoteObject 
					implements ActionListener, GarageSizeObserver {

	protected HandleVehicleEntry() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	
	private JLabel currentlyOccupied;
	JLabel enterCarNum;
	private JTextField carNumReceiver; 
	private GarageController globalGarageController  ;
	private int runtimeOccupancy;
	private static String prgArg1 ; 
	private static String prgArg2 ;
	private JFrame globalframe;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String carNum = carNumReceiver.getText();
		JFrame frame = new JFrame("Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(carNum == null || carNum.isEmpty()) {
			JOptionPane.showMessageDialog(globalframe, "Vehicle Number cannot be empty");
			return;
		}
		String receipt = "";
		try {
			receipt = globalGarageController.handleEntry(carNum);
		} catch (final Exception ex) {
			System.out.println("Unable to generated receipt num " + e );
		}
		
		if(!receipt.contains(GarageConstants.MSG_PARKING_NOT_AVAILABLE)) {
			JOptionPane.showMessageDialog(globalframe, receipt  + "\n\n GARARGE DOOR OPENED \n");
		} else {
			JOptionPane.showMessageDialog(globalframe, receipt);
		}
		
		carNumReceiver.setText("");
		int capacity = -1;
		try {
			capacity = globalGarageController.getCapacity();
			runtimeOccupancy = globalGarageController.getCurrentOccupancy();
		} catch (final Exception exception) {
			System.out.println("Unable to reterive capacity " + exception);
		}
		currentlyOccupied.setText("Current Occupancy " + runtimeOccupancy + "/" + capacity);
		globalframe.revalidate();
	}

	public static void main(String[] args) {
		prgArg1 = args[0];
		prgArg2 = args[1];
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		createAndShowGUI();
            	} catch(final Exception e) {
            		System.out.println("Unabled to run the application" + e);
            		System.exit(-1);
            	}
            }
        });
    }

	private void intializeGarage() {
		try {
			globalGarageController = (GarageController) Naming.lookup("rmi://" + prgArg1 + ":" + prgArg2 + "/GarageService");
		} catch (MalformedURLException | RemoteException | NotBoundException exceptions) {
			exceptions.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static void createAndShowGUI() throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Welcome to the Parking Garage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandleVehicleEntry app = new HandleVehicleEntry();
        Component contents = app.createComponents(frame);
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	private Component createComponents(JFrame frame) throws Exception {
		this.globalframe = frame;
		intializeGarage();
		final GarageSizeObserver garageSizeTracker = new MonitorSizeChange();
		Observable g = (Observable)(globalGarageController.getGarageSizeTracker());
		g.registerObserver(this);
		g.registerObserver(garageSizeTracker);
		JButton enterButton = new JButton("Print Receipt");
		currentlyOccupied = new JLabel("Occupancy " + globalGarageController.getCurrentOccupancy() + "/" + globalGarageController.getCapacity(), SwingConstants.CENTER);
		enterCarNum = new JLabel("Enter Vehicle Number : ", SwingConstants.LEFT);
		Font enterCarNumFont = enterCarNum.getFont();
		Font boldFont = new Font(enterCarNumFont.getFontName(), Font.BOLD, 15);
		
		enterCarNum.setFont(boldFont);
		enterButton.setFont(boldFont);
		currentlyOccupied.setFont(boldFont);
		
		enterButton.addActionListener(this);
				
		carNumReceiver = new JTextField(20);
		
		JPanel pane = new JPanel(new GridLayout(0, 1));
		pane.add(currentlyOccupied);
	    pane.add(enterCarNum);
	    pane.add(carNumReceiver);
	    pane.add(enterButton);
	    
	    
	    pane.setBorder(BorderFactory.createEmptyBorder(
	    		30, //top
                30, //left
                100, //bottom
                200) //right
                );
	    return pane;
	}

	@Override
	public void updateGarageSize(int runtimeCapacity) {
		this.runtimeOccupancy = runtimeCapacity;
		int capacity = -1;
		try {
			capacity = globalGarageController.getCapacity();
		} catch (final Exception exception) {
			System.out.println("Unable to reterive capacity " + exception);
		}
		currentlyOccupied.setText("Current Occupancy " + runtimeCapacity + "/" + capacity);
		globalframe.revalidate();
	}
}
