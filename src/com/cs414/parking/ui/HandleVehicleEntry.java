package com.cs414.parking.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.cs414.parking.controller.GarageController;
import com.cs414.parking.utils.GarageConstants;

public class HandleVehicleEntry implements ActionListener {

	JLabel currentlyOccupied;
	JLabel enterCarNum;
	JTextField carNumReceiver; 
	final GarageController garage = new GarageController();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String carNum = carNumReceiver.getText();
		JFrame frame = new JFrame("Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(carNum == null || carNum.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Vehicle Number cannot be empty");
			return;
		}
		
		final String receipt = garage.handleEntry(carNum);
		if(!receipt.contains(GarageConstants.MSG_PARKING_NOT_AVAILABLE)) {
			JOptionPane.showMessageDialog(frame, receipt  + "\n\n GARARGE DOOR OPENED \n");
		} else {
			JOptionPane.showMessageDialog(frame, receipt);
		}
		
		carNumReceiver.setText("");
		currentlyOccupied.setText("Current Occupancy " + garage.getCurrentOccupancy() + "/" + garage.getCapacity());
		
	}

	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	
	private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Welcome to the Parking Garage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandleVehicleEntry app = new HandleVehicleEntry();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	public Component createComponents() {
		JButton enterButton = new JButton("Print Receipt");
		currentlyOccupied = new JLabel("Occupancy " + garage.getCurrentOccupancy() + "/" + garage.getCapacity(), SwingConstants.CENTER);
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

}
