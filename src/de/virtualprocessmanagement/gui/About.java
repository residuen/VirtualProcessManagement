package de.virtualprocessmanagement.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class About extends JFrame
{
	JFrame frame;
	JPanel panel;
	JTextArea textArea;
	
	/**
	 * <u>Konstruktor</u><br>
	 * Erzeugen des Anzeige-Panels und der TextArea fuer den dargestellten Text
	 */
	public About()
	{
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		
		StringBuffer string = new StringBuffer();			// StringBuffer werden verwendet, wenn laengere Zeichenketten durch hintereinanderhaengen erzeugt werden sollen 
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); 	// Monospace-Font setzen
		
		string.append("\n VirtualProcessManagement: Eine virtuelle Prozess-Simulation\n");
		string.append("\n Version: 0.5 beta\n");
		string.append(" (c) Copyright K. Bettray 2011. All rights reserved.\n");
		string.append(" Lizenz: GPL 3.0 (http://www.gnu.de/documents/gpl.de.html)\n");

		textArea.setText(string.toString());
		panel.add(textArea);
		
//		setLnF();
		initFrame();		
	}
	
	/**
	 * Setzen des Look&Feel
	 */	
//	private void setLnF()
//	{
//		try {
////			UIManager.setLookAndFeel(UIManager. getCrossPlatformLookAndFeelClassName());
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
////			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
////			UIManager.setLookAndFeel("javax.swing.plaf.mac.MacLookAndFeel");
//		} catch(Exception e) {
//			try {
////				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName().toString());
//			} catch(Exception e2) {
//				System.out.println("Fehler beim Umschalten des Look&Feels/ Error while switching look&feel!");
//			}
//		}
//	}
	
	/**
	 * <u>Erzeugen des Haupt-Frames</u><br>
	 * 
	 */
	private void initFrame()
	{
		Container cp = getContentPane();
		cp.add(panel);
		frame = new JFrame();
		frame.setTitle("VirtualProcessManagement");
		frame.setSize(505, 230);
		frame.setContentPane(cp);
		frame.setLayout(new GridLayout(1,1));
		frame.setDefaultCloseOperation(JFrame. DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		ScrollColor scrollcolor = new ScrollColor();
		scrollcolor.settextArea(textArea);
		
		Thread t = new Thread(scrollcolor);
		t.start();
	}

	/**
	 * <u>Innere Klasse: ScrollColor</u><br>
	 * @author Karsten
	 * Dient der Demonstration nebenl&auml;figer Prozesse. Realisierung durch Implementation des interfaces 'Runnable'
	 */
	class ScrollColor implements Runnable
	{
		JTextArea textArea;						// Referenz auf TextArea
		
		void settextArea(JTextArea textArea)	// setzen der referenz auf TextArea
		{
			this.textArea = textArea;
		}
		
		public void run() {						// 'run' wird bei Thread-Aufruf thread.start(); automatisch ausgefuehrt
//			System.out.println("Thread startet!");
			int i = 0;

			while((i++)<255) {					// Durchlaufen der Schleife bis Zaehler 255 erreicht hat
				textArea.setBackground(new Color(i, i, i));	// Zuweisen der Hintergrundfarbe der TextArea
				
				try {
					Thread.sleep(5);			// Thread fuer 5ms einrieren
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
//			Thread ist zuende!
		}
	}
}
