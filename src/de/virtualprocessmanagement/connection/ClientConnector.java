package de.virtualprocessmanagement.connection;

import java.util.Vector;

import de.virtualprocessmanagement.interfaces.HTTPClient;

/**
 * Kommuniziert ueber ServerClientConnectionLayer mit dem Webserver
 * @author bettray
 * 
 * Client-Befehle:
 *  moveobject=<id>,<richtung>
 *  Beispiel: moveobject=43,down -> Bewegt Objekt 43 eine Zelle nach unten (Richtungen: up, down, left, right)
 *  
 *  chargeobjectbyid=<lifterId>,<loadId>,<richtung>
 *  Beispiel: chargeobjectbyid=43,51,left -> Laedt auf Objekt 43 die Ladung/Objekt 51 welches links liegt (Richtungen: up, down, left, right)
 *  
 *  dischargeobjectbyid=<lifterId>,<richtung>
 *  Beispiel: dischargeobjectbyid=43,up -> Laedt auf Objekt 43 die Ladung/Objekt 51 welches links liegt (Richtungen: up, down, left, right)
 *
 *	objectinfo=<keyWord>
 *	Beispiel: objectinfo=getall -> Gibt die Daten ALLER Objekte zurueck
 *			  objectinfo=getallstatic -> Gibt die Daten ALLER nicht-beweglichen Objekte zurueck
 *			  objectinfo=getallcharge -> Gibt die Daten ALLER beweglichen Objekte zurueck
 *  
 *  Beispiel: objectinfo=getbygroup:2,0 -> Gibt aus Gruppe 2 das 0-te Objekt zurueck
 *  
*/
public class ClientConnector extends Thread implements HTTPClient {
	
	private boolean runMode = true;
	
	private int sleepTime = 25;
	
	protected String command = "";
	
	private String data = null;
	
	protected String hostAdress = null;
	
	private Vector<String> commandList = new Vector<String>();	// Kommando-Queue, speichert die Client2Server-Anfragen zwischen
	
	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}
    
//    protected void setCommand(String command) {
//		synchronized (command) {
//			this.command = command;
//			
//			commandList.add(command);
//			
//			new WorkerThread().start();
//		}
//	}
	
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
		
//		System.out.println("ClientConnector:host="+host);
		
//		connection = new HTTPClientConnection(host);
	}

	/**
	 * fuegt Kommando-Queue den BEfehlstext fuer die naechste Serveranfrage 
	 */
	@Override
	public synchronized void sendNextRequest(String command) {
		
		this.command = "client?"+command;
			
		commandList.add( "client?"+command);	
	}
	
	@Override
	public void dataResponseEvent(String[] data) {

	}

	@Override
	public void setConnectionLayer(ServerClientConnectionLayer connectionLayer) {

	}
	
	// Methoden zum direkten erzeugen der HTTP-Kommandos
	public void moveObject(int id, String direction) {
		
		sendNextRequest("moveobject="+id+","+direction);
	}
	
	public void chargeObjectById(int lifterId, int loadId, String directionOfLoad) {
		
		sendNextRequest("chargeobjectbyid="+lifterId+","+loadId+","+directionOfLoad.toLowerCase());
	}
	
	public void dischargeObjectById(int lifterId, String directionOfLoad) {
		
		sendNextRequest("dischargeobjectbyid="+lifterId+","+directionOfLoad.toLowerCase());
	}
	
	public void getObjectInfo(String objectKey) {
		
		sendNextRequest("objectinfo="+objectKey.toLowerCase());
	}
	
	public void getObjectInfoByGroup(int objectGroup, int objectId) {
		
		sendNextRequest("objectinfo=getbygroup:"+objectGroup+","+objectId);
	}
	
	public void run() {

		new WorkerThread().start();
		
    	while(!isInterrupted() && runMode)
        {
    		try { Thread.sleep(sleepTime); } 
    		catch (InterruptedException e) { System.out.println(e.getMessage()); }
        }
    }
	
	public class WorkerThread extends Thread {
		
		private HTTPClientConnection connection = null;
		
		public void run() {
	    	
	    	while(!isInterrupted() && runMode)
	        {
	    		if(commandList.size() > 0)
	    		{
	    			connection = new HTTPClientConnection(hostAdress);
	    			
	    			// FIFO: Das oberste Kommando im Befehls-Queue wird ausgelesen und anschliessend geloescht
	    			System.out.println("WorkerThread: http://"+hostAdress+"/"+commandList.get(0));
	    			data = connection.sendRequest("http://"+hostAdress+"/"+commandList.get(0));
	    			commandList.remove(0);	// Loeschen des gesendeten Kommandos
//	    			connection.notify();
	    			connection = null;
	    		}

	    		loop(data);
	    		
	    		try { Thread.sleep(sleepTime); } 
	    		catch (InterruptedException e) { System.out.println(e.getMessage()); }
	        }
	    }	
	}
}