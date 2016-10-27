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

public class HandleGarageSize implements ActionListener {

	JLabel enterSize;
	JTextField sizeReceiver; 
	final GarageController garage = new GarageController();
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String newGarageSize = sizeReceiver.getText();
		int newIntSize;
		JFrame frame = new JFrame("Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(newGarageSize == null || newGarageSize.isEmpty() || newGarageSize == "0") {
			JOptionPane.showMessageDialog(frame, "Invalid size");
			return;
		} else {
			try {
				newIntSize = Integer.parseInt(newGarageSize);
			}catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(frame, "Invalid size");
				return;
			}
		}
		JOptionPane.showMessageDialog(frame, garage.updateParkingGarageSize(newIntSize));

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
    JFrame frame = new JFrame("Welcome to the Parking Garage  -- Size updater");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    HandleGarageSize app = new HandleGarageSize();
    Component contents = app.createComponents();
    frame.getContentPane().add(contents, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);        
	}

public Component createComponents() {
	JButton updateSize = new JButton("Update Size");
	enterSize = new JLabel("Enter New Size: ", SwingConstants.LEFT);
	Font enterCarNumFont = enterSize.getFont();
	Font boldFont = new Font(enterCarNumFont.getFontName(), Font.BOLD, 15);
	
	enterSize.setFont(boldFont);
	updateSize.setFont(boldFont);
		
	updateSize.addActionListener(this);
			
	sizeReceiver = new JTextField(20);
	
	JPanel pane = new JPanel(new GridLayout(0, 1));
	pane.add(enterSize);
    pane.add(sizeReceiver);
    pane.add(updateSize);
    
    
    pane.setBorder(BorderFactory.createEmptyBorder(
    		30, //top
            30, //left
            100, //bottom
            200) //right
            );
    return pane;
}
}
