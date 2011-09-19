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
	
	/**
	 * Setzen des Flags fuer den Run-Modes
	 * @param runMode
	 */
	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
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
	}

	/**
	 * fuegt Kommando-Queue den BEfehlstext fuer die naechste Serveranfrage 
	 */
	@Override
	public synchronized void sendNextRequest(String command) {
		
		this.command = "client?"+command;
			
		commandList.add(this.command);
	}
	
	@Override
	public void dataResponseEvent(String[] data) {

	}

	// Methoden zum direkten erzeugen der HTTP-Kommandos
	
	/**
	 * Sendet Request zum Bewegen eines  Objektes (id) in eine bestimmte Richtung (left/right/up/down)
	 */
	public void moveObject(int id, String direction) {
		
		sendNextRequest("moveobject="+id+","+direction);
	}
	
	/**
	 * Sendet Request um ein bewegliches Objekt (lifterId) mit einem anderen Objekt (loadId),
	 * welches neben/ueber/unter (directionOfLoad) ihm liegt, zu beladen.
	 * @param lifterId
	 * @param loadId
	 * @param directionOfLoad	(left/right/up/down)
	 */
	public void chargeObjectById(int lifterId, int loadId, String directionOfLoad) {
		
		sendNextRequest("chargeobjectbyid="+lifterId+","+loadId+","+directionOfLoad.toLowerCase());
	}
	
	/**
	 * Sendet Request um die Ladung auf einem beweglichen Objekt (lifterId)
	 * in eine bestimmte Richtung (left/right/up/down) (directionOfLoad) abzulegen.
	 * @param lifterId
	 * @param directionOfLoad	(left/right/up/down)
	 */
	public void dischargeObjectById(int lifterId, String directionOfLoad) {
		
		sendNextRequest("dischargeobjectbyid="+lifterId+","+directionOfLoad.toLowerCase());
	}
	
	/**
	 * Liefert Informationen anhand eines Schluesselbegriffts (objectKey)
	 * ueber die sich im Plan befindlichen Objekte zurueck
	 * @param objectKey
	 */
	public void getObjectInfo(String objectKey) {
		
		sendNextRequest("objectinfo="+objectKey.toLowerCase());
	}
	
	/**
	 * Liefert Informationen einer Objektgruppe anhand
	 * einer Schluesselnummer (objectGroup)
	 * ueber die sich im Plan befindlichen Objekte zurueck
	 * @param objectGroup
	 */
	public void getObjectInfoByGroup(int objectGroup) {
		
		sendNextRequest("objectinfo=getbygroup:"+objectGroup);
	}

	/**
	 * Liefert Informationen eines Objektes einer Objektgruppe anhand
	 * einer Schluesselnummer (objectGroup) und der Objekt-Id (objectId)
	 * ueber dieses Objekte zurueck
	 * @param objectGroup
	 * @param objectId
	 */
	public void getObjectInfoByGroup(int objectGroup, int objectId) {
		
		sendNextRequest("objectinfo=getbygroupandid:"+objectGroup+","+objectId);
	}
	
	/**
	 * Startet den Worker-Thread fuer die Server-Request
	 * als zusaetzlichen nebenlaeufigen Prozess
	 * 
	 */
	public void run() {

		new WorkerThread().start();
		
    	while(!isInterrupted() && runMode)
        {
    		try { Thread.sleep(sleepTime); } 
    		catch (InterruptedException e) { System.out.println(e.getMessage()); }
        }
    }
	
	/**
	 * 
	 * @author bettray
	 *
	 */
	public class WorkerThread extends Thread {
		
		private HTTPClientConnection connection = null;
		
		public void run() {
	    	
	    	while(!isInterrupted() && runMode)
	        {
	    		// Ueberpruefen, ob sich HTTP-Anfragen im Queue befinden
	    		if(commandList.size() > 0)
	    		{
	    			connection = new HTTPClientConnection(hostAdress);	// Neues Anfrageobjekt initialisieren
	    			
	    			// FIFO: Das oberste Kommando im Befehls-Queue wird ausgelesen und anschliessend geloescht
//	    			System.out.println("WorkerThread: http://"+hostAdress+"/"+commandList.get(0));
	    			
	    			data = connection.sendRequest("http://"+hostAdress+"/"+commandList.get(0));
	    			
	    			commandList.remove(0);	// Loeschen des gesendeten Kommandos

	    			connection = null;	// HTTP-Connection auf null setzen
	    		}

	    		loop(data);
	    		
	    		try { Thread.sleep(sleepTime); } 
	    		catch (InterruptedException e) { System.out.println(e.getMessage()); }
	        }
	    }	
	}
}