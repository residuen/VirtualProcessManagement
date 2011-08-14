package de.virtualprocessmanagement.gui;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GuiBuilder
{
	public GuiBuilder()
	{
		initLookAndFeel();
		
		System.out.println("user.home="+System.getProperty("user.home"));
		
		MainFrame mainFrame = new MainFrame("VirtualWarehouseManagement");
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		JTextField status = new JTextField();
		status.setEnabled(false);
		
//		SolvingController solvingController = new SolvingController();
		
//		solvingController.getInputComponents().put("status", status);
		
		MainPanel mainPanel = new MainPanel(); // solvingController);
		
//		MainMenu mainMenu = new MainMenu(solvingController.getInputComponents());
		
//		mainFrame.setJMenuBar(mainMenu);

		mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainFrame.getContentPane().add(status, BorderLayout.SOUTH);

		mainFrame.setVisible(true);
	}

	private void initLookAndFeel() {
		
		// Setzen des Look & Feels auf die System-Optik
		String ui = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
//		String ui = UIManager.getSystemLookAndFeelClassName();
		
//		System.out.println(System.getProperty("os.name"));
		
	
		try {
			UIManager.setLookAndFeel(ui);
		} catch (ClassNotFoundException e1) {
			alternativeLF();
		} catch (InstantiationException e1) {
			alternativeLF();
		} catch (IllegalAccessException e1) {
			alternativeLF();
		} catch (UnsupportedLookAndFeelException e1) {
			alternativeLF();
		}
	
	}
	
	private void alternativeLF()
	{
		String ui;
		
		if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			ui = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		}
		else
		{	
			ui = UIManager.getSystemLookAndFeelClassName();
		}
		// Setzen des Look & Feels auf die System-Optik
		try {
			UIManager.setLookAndFeel(ui);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
