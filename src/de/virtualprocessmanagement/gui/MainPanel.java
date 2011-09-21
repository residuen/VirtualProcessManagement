package de.virtualprocessmanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Hauptpanel, beinhaltet alle Komponeten-Panels
 * @author bettray
 *
 */
public class MainPanel extends JPanel 
{
	public MainPanel(HashMap<String, Component> inputComponents)
	{		
		initPanel(inputComponents);

//		JOptionPane.showMessageDialog(this, "Fehlerhafter Funktionsausdruck!");
	}

	private void initPanel(HashMap<String, Component> inputComponents)
	{
		setLayout(new BorderLayout());	// Layout-Manager festlegen 
		
		/*
		 *  Objekt fuer MDI-Panels intitialisieren
		 *  Es beinhaltet die Visualisierung und
		 *  die Server-Nachrichten
		 */
		JDesktopPane mdiFrame = new JDesktopPane();
		 
		 inputComponents.put("mdiframe", mdiFrame);
		
		 // Das IconMenuPanel in einen ScrollPane geben
		 JScrollPane leftScrollpane = new JScrollPane(new IconMenuPanel(inputComponents), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // contentPanel));
		 leftScrollpane.setBorder(null);

//		 add(new TopPanel(), BorderLayout.NORTH);
		 add(leftScrollpane, BorderLayout.WEST); 	// Scrollpanel mit Menupanel links in Hauptpanel einhängen
		 add(mdiFrame, BorderLayout.CENTER);		// MDI-Panel mit Visu und Server-Msg ins Center einhaengen
	}
}
