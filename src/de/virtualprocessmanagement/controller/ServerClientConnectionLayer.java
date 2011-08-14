package de.virtualprocessmanagement.controller;

import java.io.DataOutputStream;

import de.virtualprocessmanagement.client.Client;
import de.virtualprocessmanagement.interfaces.HTTPClient;
import de.virtualprocessmanagement.server.Server;

public class ServerClientConnectionLayer {
	
	private Client client = null;
	
	private Server server = null;
	
	private DataOutputStream output = null;
	
	public void clientRequest(String text, Server server, DataOutputStream output) {
		
		this.server = server;
		this.output = output;
		/*
		 * Hier kommt der Algorithmus fuer die Verarbeitung der Anfragedaten hin ...
		 */
		
		System.out.println("ServerClientConnectionLayer: Request from Client:"+text);
		
		client.dataRequestEvent(text);
	}

	public void clientResponse(String text) {
		
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
