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
	private String cmd = null;
	
//	private String command = "?client=foo&cmd=info";
	
	public HTTPClientConnection(String hostname)
	{
		this.hostname = hostname;
	}
	
	public synchronized String sendRequest(String cmd) {
		
		this.cmd = cmd;
		
		System.out.println("cmdCLIENTCONNECTOR="+cmd);
		
		try {
			url = new URL(cmd);
//			url = new URL("http://"+hostname+":"+port+"/"+cmd);
		}
		catch (MalformedURLException e) { e.printStackTrace(); } 
		
		return nextServerRequest();
	}
	
	private synchronized String nextServerRequest()
	{
		StringBuffer str = new StringBuffer();
		
		try {
			url = new URL(cmd);
//			url = new URL("http://"+hostname+":"+port+"/"+cmd);
		}
		catch (MalformedURLException e) { e.printStackTrace(); } 
		try {
			System.out.println("url="+url.toString());
			
			con = url.openConnection();
			
			scan = new Scanner(con.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNext())
		{
			str.append(scan.nextLine());
		}
		
		scan.close();
		
		con = null;

		return str.toString();
	}
	
//	public static void main(String[] arg0)
//	{
////		new HTTPBytePacketConnection("localhost");
//		new HTTPClientConnection("127.0.0.1");
//	}
}
