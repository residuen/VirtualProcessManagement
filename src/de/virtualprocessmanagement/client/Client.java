package de.virtualprocessmanagement.client;

import de.virtualprocessmanagement.connection.ClientConnector;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class Client extends ClientConnector {

	String[] testDirections = new String[] { "up", "up", "right", "up", "right", "right", "right", "right", "right", "right" };
	
	int count = 0; 
	
	@Override
	public void loop(String data) {
		
		System.out.println(count+" LOOP:Client empfaengt:\n"+data);
		
		String cmd = "";
		
//		cmd = "client?objectinfo=getall";
//		cmd = "client?objectinfo=getallstatic";
//		cmd = "client?objectinfo=getallmoveable";
//		cmd = "client?objectinfo=getbygroup:2,0";
		
		if(count < testDirections.length)
		{
			cmd = "client?moveobject=2,0,"+testDirections[count];
			count++;
		}
		else
			cmd = "";
		
//		System.out.println("nächste Anfrage des Client: "+cmd);
		
//		dataResponseEvent(new String[] { "Ueberraschungstext" });
//		sendNextRequest("client?getserverinfo");
		
		sendNextRequest(cmd);
	}

//	public static void main(String[] arg0)
//	{
//		new Client().start();
//	}

}
