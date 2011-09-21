package de.virtualprocessmanagement.visu;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import de.virtualprocessmanagement.interfaces.Message;
import de.virtualprocessmanagement.processing.ProcessMap;

/**
 * Copyright: 2011
 * @author: Karsten Bettray
 * @version 0.1
 * Licence: GPL 3.0 or higher
 * file: WebserverGui.java
*/

//file: webserver_starter.java
//declare a class wich inherit JFrame
public class VisualisationGui extends JInternalFrame implements Message, InternalFrameListener, ChangeListener {	// ActionListener
	
	static Integer listen_port = null;

//	private HTTPServer server = null;
	
//	private ServerClientConnectionLayer serverClientConnector = null;
	
	private final double[] zoomFactors= new double[] { 1, 1.5, 2, 2.5, 3, 3.5, 4 };

	   //declare some panel, scrollpanel, textarea for gui
	private JPanel contentPanel = new JPanel(new BorderLayout());
	private JPanel jPanel1 = new JPanel();
	private JScrollPane jScrollPane1 = new JScrollPane();
	private JTextArea jTextArea2 = new JTextArea();
	private VisuPanel visuPanel = new VisuPanel();
	private JSlider zoomSlider = null;
	
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
		
		zoomSlider = new JSlider( 0, zoomFactors.length-1, 2 );
		zoomSlider.addChangeListener(this);
		zoomSlider.setToolTipText("Zoomfactor "+2);
		zoomSlider.setPaintTicks( true );
		zoomSlider.setMinorTickSpacing( 1 );

		
//		JButton button = new JButton("Clear");
//		button.addActionListener(this);
	  
		visuPanel.setProcessMap(processMap, host);
		
		jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea2.setEditable(false);

		//change this to impress your friends
		this.setTitle("Visualisation");

		this.addInternalFrameListener(this);

		//add the various to the proper containers
		setLayout(new BorderLayout());
		jPanel1.setLayout(new GridLayout(1,1));
		jScrollPane1.getViewport().add(jTextArea2);
		jPanel1.add(jScrollPane1);
		
//		centerPanel.add(jPanel1);
		centerPanel.add(visuPanel);
		contentPanel.add(centerPanel, BorderLayout.CENTER);
		contentPanel.add(zoomSlider, BorderLayout.SOUTH);
		
		this.getContentPane().add(contentPanel);
		
		//tweak the apearance
		this.setSize(420, 560);
		this.setLocation(440, 10);
		this.setResizable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		
		if(processMap == null)
			this.setVisible(true);
		
		//make sure it is drawn
		this.validate();
		
//		System.out.println("Gerendert?");
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

//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		jTextArea2.setText("<VirtualProcessManagement-Client>\n<Type http://localhost/test.txt in browser to test>\n\n");
//	}
	
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

	@Override
	public void stateChanged(ChangeEvent arg0) {
		visuPanel.setZoomFactor(zoomFactors[zoomSlider.getValue()]);
		visuPanel.repaint();
	}
	
	
//	public static void main(String[] arg0)
//	{
//		Scanner input = new Scanner(System.in);
//		String hostAdress, map;
//		VisualisationGui myVisu;
//		JFrame frame = new JFrame();
//		
//		System.out.println("Geben Sie die Adresse des Host-Servers ein:");
//		
//		hostAdress = input.nextLine();
//		
//		if(hostAdress == null || hostAdress.length() == 0)
//			hostAdress = "localhost";
//
//		System.out.println("Geben Sie Pfad und Name der Map-Datei ein:");
//		
//		map = input.nextLine();
//		
//		if(map == null || map.length() == 0)
//			map = "map.csv";
//
//		myVisu = new VisualisationGui(new ProcessMap(map, 25, 25), hostAdress);
//		
//		frame.getContentPane().add(myVisu.contentPanel);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("Visualisation");
//		frame.setSize(420, 560);
//		frame.setLocation(0, 10);
//		frame.setResizable(true);
//		frame.setVisible(true);
//	}
}
