package cs414.a5.sanjusam.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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

import cs414.a5.sanjusam.controller.GarageController;

public class HandleGarageSize implements ActionListener {

	private JLabel enterSize;
	private JTextField sizeReceiver; 
	private GarageController globalGarageController ;
	private static String prgArg1;
	private static String prgArg2;
	
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
		try {
			JOptionPane.showMessageDialog(frame, globalGarageController.updateParkingGarageSize(newIntSize));
		} catch (HeadlessException | RemoteException exceptions) {
			exceptions.printStackTrace();
			System.exit(-1);
		}
		sizeReceiver.setText("");
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
	    JFrame frame = new JFrame("Welcome to the Parking Garage  -- Size updater");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    HandleGarageSize app = new HandleGarageSize();
	    Component contents = app.createComponents();
	    frame.getContentPane().add(contents, BorderLayout.CENTER);
	    frame.pack();
	    frame.setVisible(true);        
	}

	private Component createComponents() {
		intializeGarage();
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
	
	private void intializeGarage() {
		try {
			globalGarageController = (GarageController) Naming.lookup("rmi://" + prgArg1 + ":" + prgArg2 + "/GarageService");
		} catch (MalformedURLException | RemoteException | NotBoundException exceptions) {
			exceptions.printStackTrace();
			System.exit(-1);
		}
	}
}
