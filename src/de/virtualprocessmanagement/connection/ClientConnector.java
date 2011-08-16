package de.virtualprocessmanagement.connection;

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
	
	private String command = "?client=foo&cmd=info";
	
	private String data = null;
	
	private HTTPClientConnection connection = new HTTPClientConnection("127.0.0.1");
	
	private Server server = null;
	
	ServerClientConnectionLayer serverClientConnectionLayer = null;
		
	public void setServerClientConnectionLayer(ServerClientConnectionLayer serverClientConnectionLayer) {
		this.serverClientConnectionLayer = serverClientConnectionLayer;
	}

	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}
    
    protected void setCommand(String command) {
		synchronized (command) {
			this.command = command;
		}
	}
	
    /**
     * Uebergibt Daten an Child-Objekt
     */
	public void dataRequestEvent(String data) {
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

	/**
	 * Setzt den Text fuer die naechste Serveranfrage 
	 */
	@Override
	public void sendNextRequest(String command) {
		
		synchronized (command) {
			this.command = command;
		}
	}

	public void run() {
    	
    	while(!isInterrupted() && runMode)
        {    		
    		data = connection.sendRequest(command);
			dataRequestEvent(data);
    		
    		try {
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    			System.out.println(e.getMessage());
    		}
        }
    }
}
