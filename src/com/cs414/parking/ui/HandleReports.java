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
import javax.swing.SwingConstants;

import com.cs414.parking.controller.GarageController;

public class HandleReports implements ActionListener {

	JButton generateReports;
		
	final GarageController garage = new GarageController();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame("Information");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(frame, garage.handleFinancialReporting());
		
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
        JFrame frame = new JFrame("Parking Garage Reports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandleReports app = new HandleReports();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
	
	public Component createComponents() {
		generateReports = new JButton("Generate Reports");
		Font enterCarNumFont = generateReports.getFont();
		Font boldFont = new Font(enterCarNumFont.getFontName(), Font.BOLD, 15);
		generateReports.setFont(boldFont);
				
		JPanel pane = new JPanel(new GridLayout(0, 2));
		generateReports.addActionListener(this);
		
		pane.add(generateReports);
			    
	    pane.setBorder(BorderFactory.createEmptyBorder(
	    		30, //top
	            30, //left
	            100, //bottom
	            100) //right
	            );
	    return pane;
	}

}
