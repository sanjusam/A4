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
import com.cs414.parking.utils.CardValidator;
import com.cs414.parking.utils.GarageConstants;

public class HandleVehicleExit implements ActionListener {

	private JLabel enterReceiptNum;
	private JTextField receiptNumReceiver;
	private JButton enterButton;
	private JButton missingLostReceipt;
	private static String prgArg1 ; 
	private static String prgArg2 ;
	
	
	private GarageController globalGarageController;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String amtHeader = "Amount to Pay  $";
		String respFromSystem = "";
		JFrame frame = new JFrame("Information");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(e.getSource().equals(missingLostReceipt)) {
			try {
				respFromSystem = globalGarageController.handleMissingTicket();
			} catch (RemoteException exception) {
				exception.printStackTrace();
			}
		} else if (e.getSource().equals(enterButton)) {
			final String receiptNum = receiptNumReceiver.getText();
			if(receiptNum == null || receiptNum.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Receipt Number cannot be empty");
				return;
			}
			try {
				respFromSystem = globalGarageController.handleExit(receiptNum);
			} catch (RemoteException exception) {
				exception.printStackTrace();
			}
		}
		receiptNumReceiver.setText("");
		if(respFromSystem.equals(GarageConstants.INVALID_RECEIPT_NUM)) {
			JOptionPane.showMessageDialog(frame, respFromSystem);
			return;
		}
		JOptionPane.showMessageDialog(frame, amtHeader + respFromSystem);
		
		int attempt = 0;
		final int maxCardAttempt = 3;
		boolean payByCash = false;
		JFrame crediCardDetails = new JFrame("Payment");
		String paymentDetails = "";
		while (true) {
			if(++attempt > maxCardAttempt) {
				payByCash = true;
				break;
			}
			String creditCard = JOptionPane.showInputDialog(crediCardDetails, "Enter the card number");
			if(creditCard == null || creditCard.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Payment Details empty - Try Again!!");
				continue;
			} else {
				try {
					Integer.parseInt(creditCard);
					if(!CardValidator.validate(creditCard)) {
						JOptionPane.showMessageDialog(frame, "Invalid Card - Try Again!!");
						continue;
					}
				} catch (final NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "Bad Payment Details - Try Again!!");
					continue;
				}
				try {
					paymentDetails = globalGarageController.makePayment(Float.parseFloat(respFromSystem));
				} catch (NumberFormatException | RemoteException exceptions) {
					exceptions.printStackTrace();
				}
				break;
			}
		}
		if(payByCash) {
			JOptionPane.showMessageDialog(frame, "Credit card attempt failed - Pay by cash at the counter.");
		} else {
			JOptionPane.showMessageDialog(frame, paymentDetails);
		}
	}

	public static void main(String[] args) {
		prgArg1 = args[0];
		prgArg2 = args[1];
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	private static void createAndShowGUI() {
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Parking Garage Exit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandleVehicleExit app = new HandleVehicleExit();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

	private Component createComponents() {
		intializeGarage();
		enterButton = new JButton("Get Amount");
		missingLostReceipt = new JButton("Lost Receipt");
		
		enterReceiptNum = new JLabel("Enter Receipt Number : ", SwingConstants.LEFT);
		
		Font enterCarNumFont = enterReceiptNum.getFont();
		Font boldFont = new Font(enterCarNumFont.getFontName(), Font.BOLD, 15);
		
		enterReceiptNum.setFont(boldFont);
		enterButton.setFont(boldFont);
		missingLostReceipt.setFont(boldFont);
		enterButton.addActionListener(this);
		missingLostReceipt.addActionListener(this);
		
		receiptNumReceiver = new JTextField(20);
		
		JPanel pane = new JPanel(new GridLayout(0, 2));
		pane.add(enterReceiptNum);
	    pane.add(receiptNumReceiver);
	    pane.add(missingLostReceipt);
	    pane.add(enterButton);
	    
	    
	    pane.setBorder(BorderFactory.createEmptyBorder(
	    		30, //top
	            30, //left
	            100, //bottom
	            200) //right
	            );
	    return pane;
	}
	
	private void intializeGarage() {
		try {
			globalGarageController = (GarageController) Naming.lookup("rmi://" + prgArg1 + ":" + prgArg2 + "/GarageService");
		} catch (MalformedURLException | RemoteException | NotBoundException exceptions) {
			exceptions.printStackTrace();
			System.exit(-1);
		}
	}
	
}
