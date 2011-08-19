package de.virtualprocessmanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel 
{
	public MainPanel(HashMap<String, Component> inputComponents)
	{		
		initPanel(inputComponents);
		
//		JOptionPane.showMessageDialog(this, "Fehlerhafter Funktionsausdruck!");
	}

	private void initPanel(HashMap<String, Component> inputComponents)
	{
		setLayout(new BorderLayout());
		
		JDesktopPane mdiFrame = new JDesktopPane();
		 
		 inputComponents.put("mdiframe", mdiFrame);
		
		JScrollPane leftScrollpane = new JScrollPane(new IconMenuPanel(inputComponents), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // contentPanel));
		leftScrollpane.setBorder(null);

//		add(new TopPanel(), BorderLayout.NORTH);
		add(leftScrollpane, BorderLayout.WEST);
		add(mdiFrame, BorderLayout.CENTER);
	}
}
