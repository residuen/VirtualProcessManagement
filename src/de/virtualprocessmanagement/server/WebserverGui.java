package de.virtualprocessmanagement.server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
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
import de.virtualprocessmanagement.connection.apache.ElementalHttpServer;
import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.interfaces.Message;
import de.virtualprocessmanagement.tools.ServerInfos;

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
//public class WebserverGui extends JFrame implements WindowListener, Message, ActionListener {
public class WebserverGui extends JInternalFrame implements Message, ActionListener, InternalFrameListener {
	
	static Integer listen_port = null;

	private HTTPServer server = null;
	
	private ServerClientConnectionLayer simulationController = null;
	
    private ServerInfos serverInfos = new ServerInfos();

	   //declare some panel, scrollpanel, textarea for gui
    private JPanel jPanel1 = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea jTextArea2 = new JTextArea();
	
	//basic class constructor
	public WebserverGui(ServerClientConnectionLayer simulationController) {
	  
		this.simulationController = simulationController;
		
		listen_port = new Integer(80);

		try {
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	//basic class constructor
	public WebserverGui(String arg, ServerClientConnectionLayer simulationController) {
	  
		this.simulationController = simulationController;
		
		try {
			listen_port = new Integer(arg);
			//catch parse error
		}
		catch (Exception e) {
			listen_port = new Integer(80);
		}

		try {
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//set up the user interface
	private void jbInit() throws Exception {
	  
		JButton button = new JButton("Clear");
		button.addActionListener(this);
		//oh the pretty colors
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setUndecorated(true);
	  
		jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea2.setEditable(false);

		//change this to impress your friends
		this.setTitle("Server");

		this.addInternalFrameListener(this); // WindowListener(this);

		//add the various to the proper containers
		setLayout(new BorderLayout());
		jPanel1.setLayout(new GridLayout(1,1));
		jScrollPane1.getViewport().add(jTextArea2);
		jPanel1.add(jScrollPane1);
		this.getContentPane().add(jPanel1, BorderLayout.CENTER);
		this.getContentPane().add(button, BorderLayout.SOUTH);
		
		//tweak the apearance
		this.setSize(420, 560);
		this.setLocation(10, 10);
		this.setResizable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setVisible(true);
		
		//make sure it is drawn
		this.validate();
		
		//create the actual serverstuff,
		//all that is implemented in another class
		server = new Server(listen_port.intValue(), this);
//		server = new ElementalHttpServer(listen_port.intValue(), this);
		server.setSimulationController(simulationController);
	}

	//this is a method to get messages from the actual
	//server to the window
	public void message(String s) {
		jTextArea2.append(s);
		jTextArea2.setCaretPosition(jTextArea2.getDocument().getLength());
	}
	
	public HTTPServer getServer() {
		return server;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		jTextArea2.setText("<The VirtualProcessManagement-Server>\n<Type http://"+serverInfos.getServerIP()+"/client?getserverinfo in browser to test>\n\n");
	}
	
	public void setSimulationController( ServerClientConnectionLayer simulationController) {
		this.simulationController = simulationController;
	}
	
	@Override
	public void internalFrameClosed(InternalFrameEvent arg0) {
	
		try { server.getServersocket().close(); }
		  catch (IOException e) { e.printStackTrace(); }
		  
		  server.interrupt();
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent arg0) { }

	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) { }

	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) { }

	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) { }

	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) { }
	
//	@Override
//	public void windowClosed(WindowEvent arg0) {
//		  
////		  System.out.println("Server beenden!");
//		  
//		  try { server.getServersocket().close(); }
//		  catch (IOException e) { e.printStackTrace(); }
//		  
//		  server.interrupt();
//	}
	
//	@Override
//	public void windowActivated(WindowEvent arg0) { }
//
//	@Override
//	public void windowClosing(WindowEvent arg0) { }
//	
//	@Override
//	public void windowDeactivated(WindowEvent arg0) { }
//	
//	@Override
//	public void windowDeiconified(WindowEvent arg0) { }
//	@Override
//	public void windowIconified(WindowEvent arg0) { }
//	
//	@Override
//	public void windowOpened(WindowEvent arg0) { }

	//the JavaAPI entry point
	//where it starts this class if run
//	public static void main(String[] args) {
//		//start server on port x, default 80
//		//use argument to main for what port to start on
//		try {
//			listen_port = new Integer(args[0]);
//			//catch parse error
//		}
//		catch (Exception e) {
//			listen_port = new Integer(80);
//		}
//		//create an instance of this class
//		WebserverGui webserver = new WebserverGui(new ServerClientConnectionLayer(null));
//	}
}
