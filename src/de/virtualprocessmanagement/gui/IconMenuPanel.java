package de.virtualprocessmanagement.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.virtualprocessmanagement.listener.MenuListener;
import de.virtualprocessmanagement.tools.Dialog;

public class IconMenuPanel extends JPanel {
	
	/**
	 * Der Konstruktor erhaelt die HashMap, welche verschiedene
	 * Komponenten Objekte beinhaltet (Textfelder, Checkboxen, usw.)
	 * @param inputComponents
	 */
	public IconMenuPanel(HashMap<String, Component> inputComponents)
	{
		initPanel(inputComponents);
	}

	/**
	 * Initialisierung des Panels mit Buttons und Icons fuer das Menue 
	 * @param inputComponents
	 */
	private void initPanel(HashMap<String, Component> inputComponents)
	{
		setBackground(new Color(215, 215, 215));	// Hintergrundfarbe festlegen
		
		JPanel panel = null;
		JButton button = null;
		
		// GUI-Modus aus Componente lesen
		int guiMode = new Integer(((JTextField)inputComponents.get("guimode")).getText());
		
		MenuListener menuListener = new MenuListener(inputComponents);	// neuen MenuListener initialisieren

		Box vBox = Box.createVerticalBox();			// Box-Container fuer vertikales Layout anlegen
		Dimension dim = new Dimension(100, 80);	// Dimensions-Objekt fuer Groesse der Buttons
		Font font = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);	// Schriftart- und Grad festlegen

		/*
		 *  Erzeugen der Buttons
		 *  und zusammensetzen
		 *  des MenuPanels
		 */
		
		// Laden einer Prozess-Anlage
		if(guiMode==Dialog.SERVER_MODE || guiMode==Dialog.SERVER_CLIENT_MODE)
		{
			panel = new JPanel(new GridLayout(1, 1));
			button = new JButton("<html>load<br/>map</html>",
					new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/icons/drive-harddisk.png")));
			button.setFont(font);
			button.setToolTipText("load warehouse-data from file");
			button.setName("loadwarehouse");
			inputComponents.put(button.getName(), button);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			button.setPreferredSize(dim);
			button.setMinimumSize(dim);
			button.addActionListener(menuListener);
			panel.add(button);
			vBox.add(panel);
			vBox.add(Box.createVerticalStrut(5));
		}
		
		// Starten des COM-Servers
		if(guiMode==Dialog.SERVER_MODE || guiMode==Dialog.SERVER_CLIENT_MODE)
		{
			panel = new JPanel(new GridLayout(1, 1));
			button = new JButton("<html>start<br/>server</html>",
					new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/icons/network-server.png")));
			button.setFont(font);
			button.setToolTipText("start communication-server");
			button.setName("startserver");
			inputComponents.put(button.getName(), button);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			button.setPreferredSize(dim);
			button.setMinimumSize(dim);
			button.addActionListener(menuListener);
			panel.add(button);
			vBox.add(panel);
			vBox.add(Box.createVerticalStrut(5));
		}
		
		// Starten des Client/Client-Algorithmus
		if(guiMode==Dialog.CLIENT_MODE || guiMode==Dialog.SERVER_CLIENT_MODE)
		{
			panel = new JPanel(new GridLayout(1, 1));
			button = new JButton("<html>start<br/>client</html>",
					new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/icons/network-wired.png")));
			button.setFont(font);
			button.setToolTipText("manage communication-client");
			button.setName("startclient");
			inputComponents.put(button.getName(), button);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			button.setPreferredSize(dim);
			button.setMinimumSize(dim);
			button.addActionListener(menuListener);
			panel.add(button);
			vBox.add(panel);
			vBox.add(Box.createVerticalStrut(5));
		}
		
		// Starten der Visualisierung
		panel = new JPanel(new GridLayout(1, 1));
		button = new JButton("<html>connect<br/>visualisation</html>",
				new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/icons/x-office-presentation.png")));
		button.setFont(font);
		button.setToolTipText("Connect to the visualisation by IP-adress");
		button.setName("connectvisu");
		inputComponents.put(button.getName(), button);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.addActionListener(menuListener);
		panel.add(button);
		vBox.add(panel);
		vBox.add(Box.createVerticalStrut(5));
		
//		panel = new JPanel(new GridLayout(1, 1));
//		button = new JButton("<html>edit<br/>settings</html>",
//				new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/icons/preferences-system.png")));
//		button.setFont(font);
//		button.setToolTipText("<html>Edit the settings of VirtualWarehouseManager</html>");
//		button.setName("edit");
//		inputComponents.put(button.getName(), button);
//		button.setHorizontalTextPosition(SwingConstants.CENTER);
//		button.setVerticalTextPosition(SwingConstants.BOTTOM);
//		button.setPreferredSize(dim);
//		button.setMinimumSize(dim);
//		button.addActionListener(menuListener);
//		panel.add(button);
//		vBox.add(panel);
//		vBox.add(Box.createVerticalStrut(5));

		// Infos ueber das Programm
		panel = new JPanel(new GridLayout(1, 1));
		button = new JButton("<html>about</html>",
				new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/icons/help-browser.png")));
		button.setFont(font);
		button.setToolTipText("<html>Infos about VirtualProcessManagement</html>");
		button.setName("about");
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.addActionListener(menuListener);
		panel.add(button);
		vBox.add(panel);

		add(vBox);
	}
}