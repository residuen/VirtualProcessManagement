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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.virtualprocessmanagement.listener.MenuListener;

public class IconMenuPanel extends JPanel {

	private HashMap<String, Component> inputComponents = new HashMap<String,Component>();
	

	public IconMenuPanel()
	{
		initPanel();
	}

	private void initPanel()
	{
		setBackground(new Color(215, 215, 215));

//		System.out.println("TEST");
		
		MenuListener menuListener = new MenuListener(inputComponents);

		Box vBox = Box.createVerticalBox();
		Dimension dim = new Dimension(100, 80);
		JPanel panel = null;
		Font font = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		// Laden einer Prozess-Anlage
		panel = new JPanel(new GridLayout(1, 1));
		JButton button = new JButton("<html>load<br/>map</html>",
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
		
		// Starten des COM-Servers
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
		
		// Starten des Client/Cleint-Algorithmus
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
		
		// Starten der Visualisierung
		panel = new JPanel(new GridLayout(1, 1));
		button = new JButton("<html>connect to<br/>visualisation</html>",
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
		button.setToolTipText("<html>Infos about AVGui</html>");
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
