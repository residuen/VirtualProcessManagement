package de.virtualprocessmanagement.client;

import de.virtualprocessmanagement.connection.ClientConnector;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class Client extends ClientConnector {
	
	@Override
	public void dataRequestEvent(String data) {
		
		System.out.println("Client empfaengt: data="+data);
		
		dataResponseEvent("Ueberraschungstext");
		sendNextRequest("http://localhost/test.text?anhang=infotainment&"+(int)(Math.random()*100));
	}

	
	public static void main(String[] arg0)
	{
		new Client().start();
	}

}
