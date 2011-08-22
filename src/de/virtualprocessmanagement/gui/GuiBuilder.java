package de.virtualprocessmanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.virtualprocessmanagement.tools.Dialog;
import de.virtualprocessmanagement.tools.ServerInfos;

public class GuiBuilder
{
	public GuiBuilder()
	{
		initLookAndFeel();
		
		HashMap<String, Component> inputComponents = new HashMap<String,Component>();
		
		ServerInfos serverInfos = new ServerInfos();
		
		// Input the server/Client-mode an the server-ip
		Dialog inputDialog = new Dialog();
		
//		System.out.println("GuiMode="+inputDialog.getGuiMode());
		
		inputComponents.put("guimode", new JTextField(""+inputDialog.getGuiMode()));
		inputComponents.put("serveradress", inputDialog.getIpAdress());
		
		JLabel status = new JLabel();
		status.setOpaque(true);
		status.setForeground(Color.BLACK);
		status.setBackground(Color.WHITE);
		
		if(inputDialog.getGuiMode()==Dialog.CLIENT_MODE)
			status.setText("Client-name="+serverInfos.getServerName()+" Client-adress="+serverInfos.getServerIP()+" Client-cores="+serverInfos.getServerCores());
		else
			status.setText("Server-name="+serverInfos.getServerName()+" Server-adress="+serverInfos.getServerIP()+" Server-cores="+serverInfos.getServerCores());

		inputDialog.dispose();
		inputDialog = null;
		
		MainFrame mainFrame = new MainFrame("VirtualProcessManagement");
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		
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
