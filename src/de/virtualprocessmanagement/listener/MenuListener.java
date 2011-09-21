package de.virtualprocessmanagement.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;
import de.virtualprocessmanagement.gui.About;
import de.virtualprocessmanagement.processing.ProcessMap;
import de.virtualprocessmanagement.server.WebserverGui;
import de.virtualprocessmanagement.test.Client;
import de.virtualprocessmanagement.tools.Dialog;
import de.virtualprocessmanagement.visu.VisualisationGui;

public class MenuListener implements ActionListener, MouseListener
{
	public static final int CELL_WIDTH = 25, CELL_HEIGHT = 25;

	private HashMap<String,Component> inputComponents = null;
	
	private Client client = null;
	
	private WebserverGui webserverGui = null;
	
	private VisualisationGui visualisationGui = null;
	
	private ServerClientConnectionLayer serverClientConnector = null;

	private ProcessMap processMap = null;
	
	private String mapName = "map.csv";	// die Standard-Map, neue Map kann ueber FileChooser eingelsen werden
	
	private String lastSelectedPath = "";	// Speichert den Pfad der Map-Datei zwischen
	
	/**
	 * Konstruktor bekommt HashMap mit Komponentenliste uebergeben
	 * @param inputComponents
	 */
	public MenuListener(HashMap<String,Component> inputComponents)
	{
		this.inputComponents = inputComponents;
	}
	
	/**
	 * nimmt Auswahlereignisse entgegen
	 * und leitet diese an die Methode event() weiter
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// Name des gedrueckten Buttons wird an Zeichenkette uebergeben
		String cmd = ((Component) arg0.getSource()).getName();

		event(cmd, arg0);
	}
	
	private void event(String event, ActionEvent arg0)
	{
		System.out.println("cmd=" + event);	// Zu Kontrollzwecken: Ausgabe des gedrueckten Buttons
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("loadwarehouse", 0); hm.put("startserver", 1);
		hm.put("startclient", 2); hm.put("connectvisu", 3);
		hm.put("about", 4);
		
		switch(hm.get(event).intValue()) {
		
			case 0:	// "loadwarehouse"
				loadWarehouse();
				break;
				
			case 1:	// "startserver"
				startServer(arg0);
				break;
				
			case 2:	// "startclient"
				if(client == null)
					startClient((JButton)arg0.getSource());
				else
					stopClient((JButton)arg0.getSource());				
				break;
				
			case 3:	// "connectvisu"
				showVisu();
				break;
				
			case 4:	// "about"
				new About();				
				break;				
		}
	}
	
	private void loadWarehouse() {
		
		String folder = lastSelectedPath.length()==0 ? System.getProperty("user.home") : lastSelectedPath;
		
		// Auswahldialog fuer eine Map-Datei 
		JFileChooser fc = new JFileChooser(new File(folder));
		fc.setCurrentDirectory(new File(lastSelectedPath));
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.showDialog(null, "Open map-file");
		
		// Map erzeugen
		if(fc.getSelectedFile() !=null && fc.getSelectedFile().isFile())
		{
			// Einlesen einer MapDatei, insofern sie die Endung .csv beinhaltet
			if(fc.getSelectedFile().getName().toLowerCase().contains(".csv"))
			{
				mapName = fc.getSelectedFile().getName();
				
				// Erzeugen des ProcessMap-Objektes
				processMap = new ProcessMap(mapName, CELL_WIDTH, CELL_HEIGHT);
				
				// Sichern des ausgewaehlten Dateipfades
				lastSelectedPath = fc.getSelectedFile().getPath();
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Bitte ein korrekt formatiertes Map-File importieren!");
		
	}
	
	private void startServer(ActionEvent arg0) {
		
		if(webserverGui == null)
		{
			initClientEnvironment();
			connectVisu();
			
			webserverGui = new WebserverGui(serverClientConnector, processMap, visualisationGui.getVisuPanel());
			((JDesktopPane)inputComponents.get("mdiframe")).add(webserverGui);
			((JButton)arg0.getSource()).setText("<html>stop<br/>server</html>");
		}
		else {
			webserverGui.setVisible(false);
			webserverGui.dispose();
			webserverGui = null;
			
			clearClientEnvironment();
			
			if(client != null)
				stopClient((JButton)arg0.getSource());
			
			((JButton)arg0.getSource()).setText("<html>start<br/>server</html>");
		}
	}
	
	private void connectVisu() {
		
		System.out.println(((JButton)inputComponents.get("connectvisu")).getText());
			
		if(visualisationGui == null)
		{
			 initClientEnvironment();
			
			// get new visuGui and add the map and the hostname
			 if(new Integer(((JTextField)inputComponents.get("guimode")).getText()) == Dialog.CLIENT_MODE)
				 visualisationGui = new VisualisationGui(null, ((JTextField)inputComponents.get("serveradress")).getText());
			 else
				 visualisationGui = new VisualisationGui(processMap, ((JTextField)inputComponents.get("serveradress")).getText());
			
			 ((JDesktopPane)inputComponents.get("mdiframe")).add(visualisationGui);
//			 visualisationGui.getReadServerData().start();
		}
	}
	
	private void showVisu() {
		
		String visuButtonText = ((JButton)inputComponents.get("connectvisu")).getText();
		System.out.println("showVisu "+visualisationGui);
		if(visualisationGui == null)
			connectVisu();
		
//		visualisationGui.setVisible(true);
		if( visualisationGui != null && visuButtonText.equals("<html>connect<br/>visualisation</html>") ) {
		
			webserverGui.setComponent(visualisationGui.getVisuPanel());
			
			 ((JButton)inputComponents.get("connectvisu")).setText("<html>disconnect<br/>visualisation</html>");
			 visualisationGui.setVisible(true);
		}
		else {
			visualisationGui.setVisible(false);
			visualisationGui.dispose();
			visualisationGui = null;
			 ((JButton)inputComponents.get("connectvisu")).setText("<html>connect<br/>visualisation</html>");
		}
	}
	
	public void startClient(JButton button) {
		
		if(client == null)
		{
			client = new Client();
			
			initClientEnvironment();
			
			if(serverClientConnector != null)
			{
				client.setHostAdress(((JTextField)inputComponents.get("serveradress")).getText());
				serverClientConnector.setClient(client);
			}
			
			client.start();
			
			((JButton)inputComponents.get("startclient")).setText("<html>stop<br/>client</html>");
		}
	}
	
	public void stopClient(JButton button) {
		client.setRunMode(false);
		client.interrupt();
		client = null;

		((JButton)inputComponents.get("startclient")).setText("<html>start<br/>client</html>");
	}
	
	/**
	 * Initialisiert die notwendigen Verbindungen, falls notwendig
	 */
	private void initClientEnvironment() {
		
		if(processMap == null)
		{
			processMap = new ProcessMap(mapName, CELL_WIDTH, CELL_HEIGHT);
		}
		
		if(serverClientConnector == null)
		{
			serverClientConnector = new ServerClientConnectionLayer(processMap);
		}
	}
	
	private void clearClientEnvironment() {
		
		processMap.getAllObjects().clear();
		processMap = null;
		serverClientConnector.closeClient();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		String cmd = ((Component)arg0.getSource()).getName();

		event(cmd, null);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
