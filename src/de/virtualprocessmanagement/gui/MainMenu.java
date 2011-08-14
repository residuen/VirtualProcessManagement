package de.virtualprocessmanagement.gui;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.virtualprocessmanagement.listener.MenuListener;

public class MainMenu extends JMenuBar {

	public MainMenu(HashMap<String,Component> inputComponents)
	{
		super();

		initMenuBar(inputComponents);
	}

	private void initMenuBar(HashMap<String, Component> inputComponents)
	{
		MenuListener menuListener = new MenuListener(inputComponents);
		
		JMenu help = new JMenu("Hilfe");
		JMenu properties = new JMenu("Einstellungen");
		JMenu file = new JMenu("Datei");
		
		properties.setName("properties");
		properties.addMouseListener(menuListener);
		
		JMenuItem item;

		item = new JMenuItem("Lade Funktion");
		item.setName("loadFkt");
		item.addActionListener(menuListener);
		file.add(item);

		item = new JMenuItem("Speichere Funktion");
		item.setName("saveFkt");
		item.addActionListener(menuListener);
		file.add(item);

		item = new JMenuItem("Drucke Funktionsplot");
		item.setName("printFkt");
		item.addActionListener(menuListener);
		file.add(item);

		item = new JMenuItem("Exportiere Funktionsplot");
		item.setName("saveFktPlot");
		item.addActionListener(menuListener);
		file.add(item);
		
		file.addSeparator();

		item = new JMenuItem("Exit");
		item.setName("exit");
		item.addActionListener(menuListener);
		file.add(item);
		
		item = new JMenuItem("plotSolution");
		item.setName("about");
		item.addActionListener(menuListener);
		
		item = new JMenuItem("About");
		item.setName("about");
		item.addActionListener(menuListener);

		help.add(item);

		add(file);
		add(properties);
		add(help);
	}
}
