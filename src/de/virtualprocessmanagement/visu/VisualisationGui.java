package de.virtualprocessmanagement.visu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.interfaces.Message;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.processing.ProcessMap;
import de.virtualprocessmanagement.server.Server;
import de.virtualprocessmanagement.test.TestSubjects;

/**
 * The Server ist based on the Webserver coded by "Jon Berg"
 * You find ist at http://fragments.turtlemeat.com/javawebserver.php
 * I added some interfaces and extensions
 * Thanks to Jon for the basic-works
 * 
 * Copyright: 2011
 * @author: Karsten Bettray
 * @version 0.1
 * Licence: GPL 3.0 or higher
 * file: WebserverGui.java
*/

//file: webserver_starter.java
//declare a class wich inherit JFrame
public class VisualisationGui extends JInternalFrame implements Message, ActionListener, InternalFrameListener {
	
	static Integer listen_port = null;

//	private HTTPServer server = null;
	
//	private ServerClientConnectionLayer serverClientConnector = null;

	   //declare some panel, scrollpanel, textarea for gui
	private JPanel jPanel1 = new JPanel();
	private JScrollPane jScrollPane1 = new JScrollPane();
	private JTextArea jTextArea2 = new JTextArea();
	private VisuPanel visuPanel = new VisuPanel();
	
	private ReadServerData readServerData = null;
	
	//basic class constructor
	public VisualisationGui(ProcessMap processMap, String host) {
		
		listen_port = new Integer(80);

		try {
			jbInit(processMap, host);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	//basic class constructor
	public VisualisationGui(ProcessMap processMap, String host, String arg) {
	  
		try {
			listen_port = new Integer(arg);
			//catch parse error
		}
		catch (Exception e) {
			listen_port = new Integer(80);
		}

		try {
			jbInit(processMap, host);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//set up the user interface
	private void jbInit(ProcessMap processMap, String host) throws Exception {
	  
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		readServerData = new ReadServerData(this, host);
		
//		JPanel centerPanel = new JPanel(new GridLayout(2,1));
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		
		JButton button = new JButton("Clear");
		button.addActionListener(this);
		//oh the pretty colors
//		setUndecorated(true);
	  
		visuPanel.setProcessMap(processMap);
		
		jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea2.setEditable(false);

		//change this to impress your friends
		this.setTitle("Visualisation");

		this.addInternalFrameListener(this); // WindowListener(this);

		//add the various to the proper containers
		setLayout(new BorderLayout());
		jPanel1.setLayout(new GridLayout(1,1));
		jScrollPane1.getViewport().add(jTextArea2);
		jPanel1.add(jScrollPane1);
		
//		centerPanel.add(jPanel1);
		centerPanel.add(visuPanel);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(button, BorderLayout.SOUTH);
		
		//tweak the apearance
		this.setSize(420, 560);
		this.setLocation(440, 10);
		this.setResizable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setVisible(true);
		
		//make sure it is drawn
		this.validate();
	}

	//this is a method to get messages from the actual
	//server to the window
	public void message(String s) {
//		jTextArea2.append(s);
//		jTextArea2.setCaretPosition(jTextArea2.getDocument().getLength());
	}
	
	public ReadServerData getReadServerData() {
		return readServerData;
	}

	public VisuPanel getVisuPanel() {
		return visuPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		jTextArea2.setText("<VirtualProcessManagement-Client>\n<Type http://localhost/test.txt in browser to test>\n\n");
	}
	
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		 System.out.println("Visu-Client beenden!");
		  
		  readServerData.setRunMode(false);
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) { }

	@Override
	public void internalFrameClosing(InternalFrameEvent e) { }

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) { }

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e)  { }

	@Override
	public void internalFrameIconified(InternalFrameEvent e) { }

	@Override
	public void internalFrameOpened(InternalFrameEvent e) { }
}
