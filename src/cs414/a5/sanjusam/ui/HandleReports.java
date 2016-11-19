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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs414.a5.sanjusam.controller.GarageController;

public class HandleReports implements ActionListener {

	JButton generateReports;
	private GarageController garage ;
	private static String prgArg1;
	private static String prgArg2;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame("Information");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			JOptionPane.showMessageDialog(frame, garage.handleFinancialReporting());
		} catch (HeadlessException | RemoteException exception) {
			exception.printStackTrace();
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
        JFrame frame = new JFrame("Parking Garage Reports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandleReports app = new HandleReports();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
	
	public Component createComponents() {
		intializeGarage();
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

	private void intializeGarage() {
		try {
			garage  = (GarageController) Naming.lookup("rmi://" + prgArg1 + ":" + prgArg2 + "/GarageService");
		} catch (MalformedURLException | RemoteException | NotBoundException exceptions) {
			exceptions.printStackTrace();
			System.exit(-1);
		}
	}
}
