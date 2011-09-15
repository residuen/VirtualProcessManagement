package de.virtualprocessmanagement.client;

import java.util.Scanner;

import de.virtualprocessmanagement.connection.ClientConnector;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class SimpleExample extends ClientConnector {

	int count = 0; 
	
	@Override
	public void loop(String data) {
		
		System.out.println(count+" LOOP:Client empfaengt: "+data);
	
		if(count==0) {
			
//			getObjectInfo("getall");
			
			sendNextRequest("objectinfo=getall");
			
			
			
			moveObject(43, "down");
			chargeObjectById(43, 51, "left");
			moveObject(43, "up");
			moveObject(43, "up");
			moveObject(43, "up");
			moveObject(43, "up");
			moveObject(43, "up");
			moveObject(43, "right");
			moveObject(43, "right");
			dischargeObjectById(43, "up");
			moveObject(43, "left");
			moveObject(43, "left");
			moveObject(43, "left");
			moveObject(43, "up");
			moveObject(43, "down");
			moveObject(43, "right");
			moveObject(43, "right");
			moveObject(43, "right");
			chargeObjectById(43, 51, "up");
			moveObject(43, "right");
			moveObject(43, "right");
			dischargeObjectById(43, "up");
			moveObject(43, "left");
			moveObject(43, "left");
			moveObject(43, "left");
			moveObject(43, "left");
			moveObject(43, "left");
			moveObject(43, "up");
			
			count++;
		}
	}

	public static void main(String[] arg0)
	{
		Scanner input = new Scanner(System.in);
		String hostAdress;
		SimpleExample myClient;
		
		System.out.println("Geben Sie die Adresse des Host-Servers ein:");
		
		hostAdress = input.nextLine();
		
		myClient = new SimpleExample();
		
		myClient.setHostAdress(hostAdress);
		
		myClient.start();
	}

}
