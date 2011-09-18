package de.virtualprocessmanagement.connection;

import java.awt.Component;
import java.io.OutputStreamWriter;

import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.processing.ProcessManager;
import de.virtualprocessmanagement.processing.ProcessMap;
import de.virtualprocessmanagement.test.Client;

public class ServerClientConnectionLayer {
	
	private Client client = null;
	
	private HTTPServer server = null;
	
	private OutputStreamWriter output = null;
	
	private ProcessMap processMap = null;
	
	private Component component = null;
	
	public ServerClientConnectionLayer(ProcessMap processMap) {
		this.processMap = processMap;
	}
	
	public void clientRequest(final String text, HTTPServer server, OutputStreamWriter output, Component component) {
		
		this.server = server;
		this.output = output;
		this.component = component;
		
//	System.out.println("ServerClientConnectionLayer:clientRequest:"+server+" "+text);
		
		if(text.toLowerCase().contains("client?")) {
			
			final ProcessManager processManager = new ProcessManager(processMap);
			processManager.setVisuComponent(component);
			processManager.setConnectionLayer(this);

			new Thread() { public void run() {
			
				processManager.loop(text);
			}}.start();
		}
	}

	public void clientResponse(final String[] text) {
		
//		System.out.println("ServerClientConnectionLayer:clientResponse");
		
		new Thread() { public void run() {
			
			server.sendResponseText(text, output);
		}}.start();
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
	
	public Component getComponent() {
		return component;
	}
}
