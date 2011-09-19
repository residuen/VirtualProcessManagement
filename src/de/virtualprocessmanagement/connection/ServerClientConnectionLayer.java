package de.virtualprocessmanagement.connection;

import java.awt.Component;
import java.io.OutputStreamWriter;

import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.processing.ProcessManager;
import de.virtualprocessmanagement.processing.ProcessMap;
import de.virtualprocessmanagement.test.Client;

public class ServerClientConnectionLayer {
	
	private Client client = null;
	
	private HTTPServer server = null;			// Server-Objekt
	
	private OutputStreamWriter output = null;	// Ausgabe-Stream, enthaelt Verbindung zum Client
	
	private ProcessMap processMap = null;		// Beinhaltet die Daten und Grafikobjekte der Map
	
	private Component component = null;			// Componente, in der die Simulation gezeichnet wird
	
	/**
	 * Konstruktor, erhaelt die Map mit den Daten
	 * der einzelnen (grafischen) Objekte
	 * @param processMap
	 */
	public ServerClientConnectionLayer(ProcessMap processMap) {
		this.processMap = processMap;
	}

	/**
	 * Initialiert den Prozess-Manager zu einer gesendeten Client-Anfrage
	 * und startet dessen Bearbeitung in einem nebenlaeufigen Prozess (Thread)
	 * @param text
	 * @param server
	 * @param output
	 * @param component
	 */
	public void clientRequest(final String text, HTTPServer server, OutputStreamWriter output, Component component) {
		
		this.server = server;		// Server-Objekt
		this.output = output;		// Ausgabe-Stream, enthaelt Verbindung zum Client
		this.component = component;	// Componente, in der die Simulation gezeichnet wird
		
		// Prozessmanager starten, wenn die aktuelle Anfrage vom Client ist
		if(text.toLowerCase().contains("client?")) {
			
			// Initialisieren des Prozessmanagers
			final ProcessManager processManager = new ProcessManager(processMap);
			processManager.setVisuComponent(component);
			processManager.setConnectionLayer(this);

			// Uebergabe des Anfragetextes und Starten des Prozessmanagers in einem Thread 
			new Thread() { public void run() {
			
				processManager.loop(text);
			}}.start();
		}
	}

	/**
	 * Serverantwort an Client in einem Thread starten
	 * @param text
	 */
	public void clientResponse(final String[] text) {
		
		new Thread() { public void run() {
			
			// Uebergabe des Antwort-textes und des Output-Streams
			server.sendResponseText(text, output);
		}}.start();
	}
	
	/**
	 * Uebergabe des Clients
	 * @param client
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	/**
	 * Unterbrechen und loeschen des Clients
	 */
	public void closeClient() {
		
		if(client != null) {
			client.setRunMode(false);
			client.interrupt();
			client = null;
		}
	}
	
	/**
	 * Existenz des/eines Clients erfragen 
	 * @return
	 */
	public boolean isClient() {
		return client!=null;
	}
	
	/**
	 * Zuweisen des verwendeten Servers
	 * @param server
	 */
	public void setServer(HTTPServer server) {
		this.server = server;
	}
	
	/**
	 * Zuweisen der Visualisierungs-Komponente
	 * @return
	 */
	public Component getComponent() {
		return component;
	}
}
