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

/**
 * Initialisiert wichtige Objekte
 * setzt das Look&Feel und baut die GUI auf
 * @author bettray
 */
public class GuiBuilder
{
	public GuiBuilder()
	{
		initLookAndFeel();	// Setzen des Look & Feels
		
		HashMap<String, Component> inputComponents = new HashMap<String,Component>(); // Nimmt verschiedene Compontenten auf
		
		ServerInfos serverInfos = new ServerInfos();	// Objekt, um Infos vom HTTP-Server zur Verfuegung zu stellen
		
		// Eingabe des GUI-Modus und der Server-IP, falls erforderlich
		Dialog inputDialog = new Dialog();
		
//		System.out.println("GuiMode="+inputDialog.getGuiMode());
		
		inputComponents.put("guimode", new JTextField(""+inputDialog.getGuiMode()));	// Komponente mit GUI-Modus sichern
		inputComponents.put("serveradress", inputDialog.getIpAdress());					// Komponente mit Serveradresse sichern
		
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
