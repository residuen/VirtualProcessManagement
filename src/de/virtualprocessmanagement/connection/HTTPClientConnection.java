package de.virtualprocessmanagement.connection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Class: HTTPClientConnection.java
 * @author bettray
 *
 * Verbindet sich mit dem Server und stellt nach Aufforderung HTTP-Anfragen.
 * Die Serverantwort wird eingelesen und gespeichert
 */
public class HTTPClientConnection {

	private URL url = null;
	private URLConnection con = null;
	private Scanner scan = null;
	private String hostname = "localhost";
	private int port = 80;
	
//	private String command = "?client=foo&cmd=info";
	
	public HTTPClientConnection(String hostname)
	{
		this.hostname = hostname;
	}
	
	public String sendRequest(String cmd) {
		
		try {
			url = new URL("http://"+hostname+":"+port+"/"+cmd);
		}
		catch (MalformedURLException e) { e.printStackTrace(); } 
		
		return nextServerRequest();
	}
	
	private String nextServerRequest()
	{
		StringBuffer str = new StringBuffer();
		
		try {
			con = url.openConnection();
			
			scan = new Scanner(con.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNext())
		{
//			str += scan.nextLine();
			str.append(scan.nextLine());
		}
		
		scan.close();
		
//		return str;
		return str.toString();
	}
	
//	public static void main(String[] arg0)
//	{
////		new HTTPBytePacketConnection("localhost");
//		new HTTPClientConnection("127.0.0.1");
//	}
}
