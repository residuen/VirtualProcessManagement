package de.virtualprocessmanagement.test;

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
	
	int count = 0; 
	
	@Override
	public void loop(String data) {
		
//		if(data != null) 
		{
			System.out.println(count+" LOOP:Client empfaengt: "+data);
		
			String cmd = "";
			
	//		cmd = "client?objectinfo=getall";
	//		cmd = "client?objectinfo=getallstatic";
//			cmd = "client?objectinfo=getallcharge";
	//		cmd = "client?objectinfo=getbygroup:2,0";
			
			sendNextRequest("objectinfo=getall");
			
			if(count==0)
				for(String s : testDirections)
					sendNextRequest(s);
			count++;

			
//			if(count < testDirections.length)
//			{
//				cmd = testDirections[count];					// Befehlskette
//				sendNextRequest(cmd);
//				count++;
//			}
//			else
//				cmd = "";
	
	
			
	//		System.out.println("nächste Anfrage des Client: "+cmd);
			
	//		dataResponseEvent(new String[] { "Ueberraschungstext" });
	//		sendNextRequest("client?getserverinfo");
						
//			sendNextRequest(cmd);
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
