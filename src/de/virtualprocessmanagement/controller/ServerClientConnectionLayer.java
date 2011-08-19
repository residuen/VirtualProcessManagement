package de.virtualprocessmanagement.controller;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import de.virtualprocessmanagement.client.Client;
import de.virtualprocessmanagement.processing.ProcessManager;
import de.virtualprocessmanagement.server.Server;

public class ServerClientConnectionLayer {
	
	private Client client = null;
	
	private Server server = null;
	
	private DataOutputStream output = null;
	
	private ProcessManager processManager = null;
	
	public ServerClientConnectionLayer(ProcessManager processManager) {
		this.processManager = processManager;
	}
	
	public void clientRequest(String text, Server server, DataOutputStream output) {
		
		this.server = server;
		this.output = output;
		
//		processManager = new ProcessManager(this);
		/*
		 * Hier kommt der Algorithmus fuer die Verarbeitung der Anfragedaten hin ...
		 */
		
		System.out.println("ServerClientConnectionLayer: Request from Client:"+text);
		
		if(text.toLowerCase().contains("client?"))
			processManager.loop(text);
		else		
			if(text.toLowerCase().equals("client?getserverinfo"))
			{
				InetAddress inetAdress = null;
				try { inetAdress = InetAddress.getLocalHost(); }
				catch (UnknownHostException e) { e.printStackTrace(); }
				
				server.sendResponseText(new String[] { "\nserver-home="+System.getProperty("user.home")+";",
													   "\nserver-ip="+inetAdress.getHostAddress()+";",
													   "\nserver-name="+inetAdress.getHostName()+";",
													   "\nserver-cores="+Runtime.getRuntime().availableProcessors() },
													   output);
			}
			else		
				if(text.toLowerCase().equals("client?getserverinfoashtml"))
				{
					InetAddress inetAdress = null;
					try { inetAdress = InetAddress.getLocalHost(); }
					catch (UnknownHostException e) { e.printStackTrace(); }
				
					server.sendResponseText(new String[] { "<html>\n<body>\nserver-home="+System.getProperty("user.home")+"<br>",
														   "server-ip="+inetAdress.getHostAddress()+"<br>",
														   "server-name="+inetAdress.getHostName()+"<br>",
														   "server-cores="+Runtime.getRuntime().availableProcessors()+"\n</body>\n</html>" },
														   output);
			}
//			else
//				client.dataRequestEvent(text);
	}

	public void clientResponse(String[] text) {
		
		server.sendResponseText(text, output);
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void closeClient() {
		
		if(client != null) {
			client.setRunMode(false);
			client.interrupt();
			client = null;
		}
	}
	
	public boolean isClient() {
		return client!=null;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
}
