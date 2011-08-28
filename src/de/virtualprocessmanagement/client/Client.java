package de.virtualprocessmanagement.client;

import de.virtualprocessmanagement.connection.ClientConnector;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class Client extends ClientConnector {

//	String[] testDirections = new String[] { "up", "up", "right", "up", "right", "right", "right", "right" };
	String[] testDirections = new String[] { "up" }; //, "down" }; // , "up", "down" };
	
	int count = 0; 
	
	@Override
	public void loop(String data) {
		
//		if(data != null) 
		{
			System.out.println(count+" LOOP:Client empfaengt:\n"+data);
		
			String cmd = "";
			
	//		cmd = "client?objectinfo=getall";
	//		cmd = "client?objectinfo=getallstatic";
	//		cmd = "client?objectinfo=getallmoveable";
	//		cmd = "client?objectinfo=getbygroup:2,0";
			
			if(count < testDirections.length)
			{
	//			cmd = "client?moveobject=2,0,"+testDirections[count];	// Objekt ueber group & id bewegen 
	//			cmd = "client?moveobject=33,"+testDirections[count];	// objekt dirket ueber id bewegen
//				cmd = "client?moveobject=43,"+testDirections[count]; 	// Den Gabelstapler bewegen
				cmd = "client?chargeobject=43,"+testDirections[count];	// Die Gabel des Staplers ausfahren & beladen
				count++;
			}
			else
				cmd = "";
	
	
			
	//		System.out.println("nächste Anfrage des Client: "+cmd);
			
	//		dataResponseEvent(new String[] { "Ueberraschungstext" });
	//		sendNextRequest("client?getserverinfo");
			
			sendNextRequest(cmd);
		}
	}

//	public static void main(String[] arg0)
//	{
//		new Client().start();
//	}

}
