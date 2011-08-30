package de.virtualprocessmanagement.connection;

import java.util.Vector;

import de.virtualprocessmanagement.interfaces.HTTPClient;

/**
 * Kommuniziert ueber ServerClientConnectionLayer mit dem Webserver
 * @author bettray
 *
 */
public class ClientConnector extends Thread implements HTTPClient {
	
	private boolean runMode = true;
	
	private int sleepTime = 25; // 1000;
	
	protected String command = "";
	
	private String data = null;
	
	private HTTPClientConnection connection = null;
	
	protected String hostAdress = null;
	
	private Vector<String> commandList = new Vector<String>();	// Kommando-Queue, speichert die Client2Server-Anfragen zwischen
	
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
	 * Fuegt die Adresse des Server-Hosts zu
	 */
	@Override
	public void setHostAdress(String host) {
	
		if(host == null || host.length() == 0)
			host = "localhost";
		
		this.hostAdress = host;
		
		connection = new HTTPClientConnection(host);
	}

	/**
	 * fuegt Kommando-Queue den BEfehlstext fuer die naechste Serveranfrage 
	 */
	@Override
	public void sendNextRequest(String command) {
		
		synchronized (command) {
			this.command = command;
			
			commandList.add(command);	
		}
	}
	
	@Override
	public void dataResponseEvent(String[] data) {

	}

	@Override
	public void setConnectionLayer(ServerClientConnectionLayer connectionLayer) {

	}
	public void run() {
    	
    	while(!isInterrupted() && runMode)
        {
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
