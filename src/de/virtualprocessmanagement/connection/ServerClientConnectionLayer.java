package de.virtualprocessmanagement.connection;

import java.io.OutputStreamWriter;

import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.processing.ProcessManager;
import de.virtualprocessmanagement.processing.ProcessMap;
import de.virtualprocessmanagement.test.Client;

public class ServerClientConnectionLayer {
	
	private Client client = null;
	
	private HTTPServer server = null;
	
	private OutputStreamWriter output = null;	// DataOutputStream
	
	private ProcessMap processMap = null;
	
	public ServerClientConnectionLayer(ProcessMap processMap) {
		this.processMap = processMap;
	}
	
	public void clientRequest(String text, HTTPServer server, OutputStreamWriter output) {
		
		this.server = server;
		this.output = output;
		
//	System.out.println("ServerClientConnectionLayer:clientRequest:"+server+" "+text);
		
		if(text.toLowerCase().contains("client?")) {
			ProcessManager processManager = new ProcessManager(processMap);
			processManager.setConnectionLayer(this);
			processManager.loop(text);
		}
	}

	public void clientResponse(String[] text) {
		
		System.out.println("ServerClientConnectionLayer:clientResponse");
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
