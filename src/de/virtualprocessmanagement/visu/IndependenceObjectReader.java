package de.virtualprocessmanagement.visu;

import de.virtualprocessmanagement.connection.HTTPClientConnection;

/**
 * holt sich die objektinformationen und erstellt daraus die Shapes.
 * DIe Shapes werden in einem Festen Interwall aktualisiert.
 * @author bettray
 */
public class IndependenceObjectReader extends Thread {
	
	private HTTPClientConnection clientConnection = null;
	
	private String host = null;
	
	private long count = 0;
	
	public IndependenceObjectReader(String host) {
		
		this.host = host;
		
		clientConnection = new HTTPClientConnection(host);
	}
	
	public void getObjectsFromServer()
	{
		String[] answer = clientConnection.sendRequest("http://"+host+"/client?objectinfo=getall").split("\\[");
		
		for(String s : answer);
//			System.out.println(s.replace("]", ""));
		
		System.out.println((count++)+": Abfrage durchgefuehrt");
	}
	
	public void run() {
		
		while(!isInterrupted()) {
			
			getObjectsFromServer();
			
			try { sleep(100); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}