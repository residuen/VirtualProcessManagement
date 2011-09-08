package de.virtualprocessmanagement.client;

import java.util.Scanner;

import de.virtualprocessmanagement.connection.ClientConnector;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class Client extends ClientConnector {

	String[] testDirections = new String[] { "moveobject=43,down",
											 "chargeobjectbyid=43,51,left",
											 "moveobject=43,up",
											 "moveobject=43,up",
											 "moveobject=43,up",
											 "moveobject=43,up",
											 "moveobject=43,up",
											 "moveobject=43,right",
											 "moveobject=43,right",
											 "dischargeobjectbyid=43,up",
											 "moveobject=43,left",
											 "moveobject=43,left",
											 "moveobject=43,left",
											 "moveobject=43,up",
											 "moveobject=43,down",
											 "moveobject=43,right",
											 "moveobject=43,right",
											 "moveobject=43,right",
											 "chargeobjectbyid=43,51,up",
											 "moveobject=43,right",
											 "moveobject=43,right",
											 "dischargeobjectbyid=43,up",
											 "moveobject=43,left",
											 "moveobject=43,left",
											 "moveobject=43,left",
											 "moveobject=43,left",
											 "moveobject=43,left",
											 "moveobject=43,up"};
	
//	String[] testDirections = new String[] { "moveobject=33,down",
//			 "moveobject=33,up",
//			 "moveobject=33,up",
//			 "moveobject=33,up",
//			 "moveobject=33,up",
//			 "moveobject=33,down",
//			 "moveobject=33,down",
//			 "moveobject=33,down"};

	
	int count = 0; 
	
	@Override
	public void loop(String data) {
		
//		if(data != null) 
		{
			System.out.println(count+" LOOP:Client empfaengt:\n"+data);
		
			String cmd = "";
			
	//		cmd = "client?objectinfo=getall";
	//		cmd = "client?objectinfo=getallstatic";
//			cmd = "client?objectinfo=getallcharge";
	//		cmd = "client?objectinfo=getbygroup:2,0";
			
			if(count < testDirections.length)
			{
	//			cmd = "client?moveobject=2,0,"+testDirections[count];	// Objekt ueber group & id bewegen 
	//			cmd = "client?moveobject=33,"+testDirections[count];	// objekt dirket ueber id bewegen
//				cmd = "client?moveobject=43,"+testDirections[count]; 	// Den Gabelstapler bewegen
//				cmd = "client?chargeobject=43,"+testDirections[count];	// Die Gabel des Staplers ausfahren & beladen
				cmd = "client?"+testDirections[count];					// Befehlskette
				count++;
			}
			else
				cmd = "";
	
	
			
	//		System.out.println("n�chste Anfrage des Client: "+cmd);
			
	//		dataResponseEvent(new String[] { "Ueberraschungstext" });
	//		sendNextRequest("client?getserverinfo");
			
			sendNextRequest(cmd);
		}
	}

	public static void main(String[] arg0)
	{
		Scanner input = new Scanner(System.in);
		String hostAdress;
		Client myClient;
		
		System.out.println("Geben Sie die Adresse des Host-Servers ein:");
		
		hostAdress = input.nextLine();
		
		myClient = new Client();
		
		myClient.setHostAdress(hostAdress);
		
		myClient.start();
	}

}
