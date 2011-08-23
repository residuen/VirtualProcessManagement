package de.virtualprocessmanagement.connection;

import java.util.Vector;

import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPClient;
import de.virtualprocessmanagement.server.Server;

/**
 * Kommuniziert ueber ServerClientConnectionLayer mit dem Webserver
 * @author bettray
 *
 */
public class ClientConnector extends Thread implements HTTPClient {
	
	private boolean runMode = true;
	
	private int sleepTime = 1000;
	
	protected String command = "";
	
	private String data = null;
	
	private HTTPClientConnection connection = null;
	
	private Server server = null;
	
	private ServerClientConnectionLayer serverClientConnectionLayer = null;
	
	protected String hostAdress = null;
	
	private Vector<String> commandList = new Vector<String>();	// Speichert die Client2Server-Anfragen ziwschen
		
	public void setServerClientConnectionLayer(ServerClientConnectionLayer serverClientConnectionLayer, String host) {
	
		this.serverClientConnectionLayer = serverClientConnectionLayer;
		this.hostAdress = host;
		
		connection = new HTTPClientConnection(host);
	}

	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}
    
    protected void setCommand(String command) {
		synchronized (command) {
			this.command = command;
			
			commandList.add(command);
		}
	}
	
    /**
     * Uebergibt Daten an Child-Objekt
     */
	public void loop(String data) {
		System.out.println("HTTPClientConnection: Request: Vater");
	}
	
	/**
	 * Empfaengt fuer Server vorgesehene Daten und gibt diese an simulationsController weiter
	 */
	public void dataResponseEvent(String[] data) {
//		System.out.println("HTTPClientConnection: Response: Vater");
		
//		server.dataResponseEvent(data)
		
		serverClientConnectionLayer.clientResponse(data);
	}
	
	public void setServer(Server server) {
		this.server = server;
	}

	protected void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	protected int getSleepTime() {
		return sleepTime;
	}
	
	protected String getHostAdress() {
		return hostAdress;
	}

	/**
	 * Setzt den Text fuer die naechste Serveranfrage 
	 */
	@Override
	public void sendNextRequest(String command) {
		
		synchronized (command) {
			this.command = command;
			
			commandList.add(command);
			
//			System.out.println("1*command="+command);			
		}
	}

	public void run() {
    	
    	while(!isInterrupted() && runMode)
        {
//    		this.command = "http://"+getHostAdress()+"/client?getrobots";
    		
//    		System.out.println("command="+this.command);
    		
//    		if(this.command != null && this.command.length()>0)
//    			data = connection.sendRequest("http://"+hostAdress+"/"+this.command);
  
    		if(commandList.size() > 0)
    		{
    			// FIFO: Das oberste Kommando im Befehls-Queue wird ausgelsen und anschliessend geloescht
    			data = connection.sendRequest("http://"+hostAdress+"/"+commandList.get(0));
    			commandList.remove(0);	// Loeschen des gesendeten Kommandos
    		}

    		loop(data);
    		
    		try {
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    			System.out.println(e.getMessage());
    		}
        }
    }
}
