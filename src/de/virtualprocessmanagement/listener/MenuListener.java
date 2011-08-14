package de.virtualprocessmanagement.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.virtualprocessmanagement.client.Client;
import de.virtualprocessmanagement.connection.HTTPClientConnection;
import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;
import de.virtualprocessmanagement.gui.About;
import de.virtualprocessmanagement.server.WebserverGui;
import de.virtualprocessmanagement.test.TestVisu;

public class MenuListener implements ActionListener, MouseListener
{
	private HashMap<String,Component> inputComponents = null;
	
	private TestVisu testVisu = null;
	
	private Client client = null;
	
	private WebserverGui webserverGui = null;
	
	private ServerClientConnectionLayer simulationController = null;
	
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
//			String fileName = JFileChooser.OPEN_DIALOG;
			
			JFileChooser fc = new JFileChooser(new File(System.getProperty("user.home")));
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.showDialog(null, "Open warehouse-file");
			
		}
		else
			if(event.equals("startserver"))
				if(webserverGui == null)
				{
					simulationController = new ServerClientConnectionLayer();
					webserverGui = new WebserverGui(simulationController); // inputComponents.get("plotprop").setVisible(true);
					((JButton)arg0.getSource()).setText("<html>stop<br/>server</html>");
				}
				else {
					webserverGui.setVisible(false);
					webserverGui.dispose();
					webserverGui = null;
					
					simulationController.closeClient();
					simulationController = null;
					
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
								if(testVisu == null)
								{
									testVisu = new TestVisu();
									testVisu.start();
									
									((JButton)arg0.getSource()).setText("<html>disconnect <br/>visualisation</html>");
								}
								else {
									
									testVisu.setRunMode(false);
									testVisu.interrupt();
									testVisu = null;
									
									((JButton)arg0.getSource()).setText("<html>connect <br/>visualisation</html>");
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
			
			if(simulationController != null)
			{
				client.setServerClientConnectionLayer(simulationController);
				simulationController.setClient(client);
			}
			
			client.start();
			
			((JButton)inputComponents.get("startclient")).setText("<html>stop<br/>client</html>");
		}
	}
	
	public void stopClient(JButton button) {
		client.setRunMode(false);
		client.interrupt();
		client = null;
		
		if(simulationController!=null && simulationController.isClient())
			simulationController.closeClient();
		
		((JButton)inputComponents.get("startclient")).setText("<html>start<br/>client</html>");
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		String cmd = ((Component) arg0.getSource()).getName();

		event(cmd, null);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

}
