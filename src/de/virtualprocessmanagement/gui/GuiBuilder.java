package de.virtualprocessmanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.virtualprocessmanagement.tools.Dialog;

public class GuiBuilder
{
	public GuiBuilder()
	{
		initLookAndFeel();
		
		HashMap<String, Component> inputComponents = new HashMap<String,Component>();
		
		// Input the server/Client-mode an the server-ip
		Dialog inputDialog = new Dialog();
		
		inputComponents.put("servermode", inputDialog.getServerMode());
		inputComponents.put("serveradress", inputDialog.getIpAdress());
		
		inputDialog.dispose();
		inputDialog = null;
		
		MainFrame mainFrame = new MainFrame("VirtualProcessManagement");
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		JTextField status = new JTextField();
		status.setEnabled(false);
		
		MainPanel mainPanel = new MainPanel(inputComponents);
		
		mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainFrame.getContentPane().add(status, BorderLayout.SOUTH);

		mainFrame.setVisible(true);
		mainFrame.getContentPane().validate();
	}

	private void initLookAndFeel() {
		
		// Setzen des Look & Feels auf die System-Optik
		String ui = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	
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
