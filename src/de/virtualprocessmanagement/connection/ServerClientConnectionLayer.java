package de.virtualprocessmanagement.connection;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import de.virtualprocessmanagement.client.Client;
import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.processing.ProcessManager;
import de.virtualprocessmanagement.server.Server;

public class ServerClientConnectionLayer {
	
	private Client client = null;
	
	private HTTPServer server = null;
	
	private OutputStreamWriter output = null;	// DataOutputStream
	
	private ProcessManager processManager = null;
	
	public ServerClientConnectionLayer(ProcessManager processManager) {
		this.processManager = processManager;
	}
	
	public void clientRequest(String text, HTTPServer server, OutputStreamWriter output) {
		
		this.server = server;
		this.output = output;
		
	System.out.println("ServerClientConnectionLayer: Request from Client:"+text);
		
		if(text.toLowerCase().contains("client?"))
			processManager.loop(text);
//		else		
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
	
	public void setServer(HTTPServer server) {
		this.server = server;
	}
}
