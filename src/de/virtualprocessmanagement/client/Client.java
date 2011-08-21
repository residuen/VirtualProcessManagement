package de.virtualprocessmanagement.client;

import de.virtualprocessmanagement.connection.ClientConnector;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class Client extends ClientConnector {

	@Override
	public void loop(String data) {
		
		System.out.println("LOOP:Client empfaengt:\n"+data);
		
		dataResponseEvent(new String[] { "Ueberraschungstext" });
//		sendNextRequest("http://localhost/client?getserverinfo");
		sendNextRequest("http://localhost/client?moveobject=2,0,up");

	}

//	public static void main(String[] arg0)
//	{
//		new Client().start();
//	}

}
