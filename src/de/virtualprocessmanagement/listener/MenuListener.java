package de.virtualprocessmanagement.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.virtualprocessmanagement.client.Client;
import de.virtualprocessmanagement.connection.HTTPClientConnection;
import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;
import de.virtualprocessmanagement.gui.About;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.processing.ProcessManager;
import de.virtualprocessmanagement.server.WebserverGui;
import de.virtualprocessmanagement.test.TestVisu;
import de.virtualprocessmanagement.visu.VisualisationGui;

public class MenuListener implements ActionListener, MouseListener
{
	private HashMap<String,Component> inputComponents = null;
	
	private TestVisu testVisu = null;
	
	private Client client = null;
	
//	private JDesktopPane mdiFrame = null;
	
	private WebserverGui webserverGui = null;
	
	private VisualisationGui visualisationGui = null;
	
	private ServerClientConnectionLayer serverClientConnector = null;
	
	private ProcessManager processManager = null;
	
	private String lastOpenPath = "";
	
//	public MenuListener()
//	{
//		this.inputComponents = null;
//	}
	
	public MenuListener(HashMap<String,Component> inputComponents)
	{
		this.inputComponents = inputComponents;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = ((Component) arg0.getSource()).getName();

		event(cmd, arg0);
	}
	
	private void event(String event, ActionEvent arg0)
	{
		System.out.println("cmd=" + event);
		
		if(event.equals("loadwarehouse"))
		{
			JFileChooser fc = new JFileChooser(new File(System.getProperty("user.home")));
			fc.setCurrentDirectory(new File(lastOpenPath));
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.showDialog(null, "Open map-file");
			
			if(fc.getSelectedFile() !=null && fc.getSelectedFile().isFile())
			{
				if(fc.getSelectedFile().getName().toLowerCase().contains(".csv"))
				{
					processManager = new ProcessManager(fc.getSelectedFile().getName());
					
					lastOpenPath = fc.getSelectedFile().getPath();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "Bitte ein korrekt formatiertes Map-File importieren!");
			
		}
		else
			if(event.equals("startserver"))
				if(webserverGui == null)
				{
					if(processManager == null)
					{
						processManager = new ProcessManager("map.csv");
					}
					
					serverClientConnector = new ServerClientConnectionLayer(processManager);
					processManager.setConnectionLayer(serverClientConnector);
					webserverGui = new WebserverGui(serverClientConnector); // inputComponents.get("plotprop").setVisible(true);
					((JDesktopPane)inputComponents.get("mdiframe")).add(webserverGui);
					((JButton)arg0.getSource()).setText("<html>stop<br/>server</html>");
				}
				else {
					webserverGui.setVisible(false);
					webserverGui.dispose();
					webserverGui = null;
					
					serverClientConnector.closeClient();
					serverClientConnector = null;
					
					if(client != null)
						stopClient((JButton)arg0.getSource());
					
					((JButton)arg0.getSource()).setText("<html>start<br/>server</html>");
				}
				else
					if(event.equals("startclient"))
					{
						if(client == null)
							startClient((JButton)arg0.getSource());
						else
							stopClient((JButton)arg0.getSource());
					}
					else
						if(event.equals("connectvisu"))
						{
							if(visualisationGui == null)
							{
//								System.out.println(objectList);
								
								// get new visuGui and add the map and the hostname
								visualisationGui = new VisualisationGui(processManager.getProcessMap(), ((JTextField)inputComponents.get("serveradress")).getText());
								processManager.setVisuComponent(visualisationGui.getVisuPanel());
								((JDesktopPane)inputComponents.get("mdiframe")).add(visualisationGui);
								visualisationGui.getReadServerData().start();
								
								((JButton)inputComponents.get("connectvisu")).setText("<html>disconnect<br/>visualisation</html>");
							}
							else {
								visualisationGui.setVisible(false);
								visualisationGui.dispose();
								visualisationGui = null;
								
								((JButton)inputComponents.get("connectvisu")).setText("<html>connect to<br/>visualisation</html>");
							}
						}
						else
							if(event.equals("about"))
								new About();
	}
	
	public void startClient(JButton button) {
		
		if(client == null)
		{
			client = new Client();
			
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
		
		if(serverClientConnector!=null && serverClientConnector.isClient())
			serverClientConnector.closeClient();
		
		((JButton)inputComponents.get("startclient")).setText("<html>start<br/>client</html>");
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
