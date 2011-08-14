package de.virtualprocessmanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;

public class MainPanel extends JDesktopPane
{
	
	public MainPanel()
	{		
		initPanel();
		
//		JOptionPane.showMessageDialog(this, "Fehlerhafter Funktionsausdruck!");
	}

	private void initPanel()
	{
		setLayout(new BorderLayout());
//		setBackground(Color.WHITE);
		
//		ContentPanel contentPanel = new ContentPanel();
		
		JScrollPane leftScrollpane = new JScrollPane(new IconMenuPanel(), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // contentPanel));
		leftScrollpane.setBorder(null);

//		add(new TopPanel(), BorderLayout.NORTH);
		add(leftScrollpane, BorderLayout.WEST);
//		add(contentPanel, BorderLayout.CENTER);
	}
}
