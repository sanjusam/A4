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

import com.cs414.parking.Garage;
import com.cs414.parking.GarageConstants;

public class HandleVehicleExit implements ActionListener {

	JLabel enterReceiptNum;
	JTextField receiptNumReceiver;
	JButton enterButton;
	JButton missingLostReceipt;
	
	final Garage garage = new Garage();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String amtHeader = "Amount to Pay  $";
		String respFromSystem = "";
		JFrame frame = new JFrame("Information");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(e.getSource().equals(missingLostReceipt)) {
			respFromSystem = garage.handleMissingTicket();
		} else if (e.getSource().equals(enterButton)) {
			final String receiptNum = receiptNumReceiver.getText();
			if(receiptNum == null || receiptNum.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Receipt Number cannot be empty");
				return;
			}
			respFromSystem = garage.handleExit(receiptNum);
		}
		receiptNumReceiver.setText("");
		if(respFromSystem.equals(GarageConstants.INVALID_RECEIPT_NUM)) {
			JOptionPane.showMessageDialog(frame, respFromSystem);
			return;
		}
		JOptionPane.showMessageDialog(frame, amtHeader + respFromSystem);
		
		JFrame crediCardDetails = new JFrame("Payment");
		String paymentDetails = "";
		while (true) {
			String creditCard = JOptionPane.showInputDialog(crediCardDetails, "Enter the card number");
			if(creditCard == null || creditCard.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Payment Details empty - Try Again!!");
				continue;
			} else {
				try {
					Integer.parseInt(creditCard);
				} catch (final NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "Bad Payment Details - Try Again!!");
					continue;
				}
				paymentDetails = garage.makePayment(Float.parseFloat(respFromSystem));
				break;
			}
		}
		JOptionPane.showMessageDialog(frame, paymentDetails);
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
        JFrame frame = new JFrame("Parking Garage Exit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandleVehicleExit app = new HandleVehicleExit();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

	public Component createComponents() {
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
}