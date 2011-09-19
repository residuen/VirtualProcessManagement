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

	private URL url = null;					// URL der Serververbindung
	private URLConnection con = null;		// Verbindungsobjekt zum Server
	private Scanner scan = null;			// Scanner-Objekt zum Einlesen der Server-Antworten
	private String hostname = "localhost";	// Serveradresse, falls keine andere eingegeben wurde
	private int port = 80;					// Port des HTTP-Servers

	/**
	 * Konstruktor mit Uebergabe der Serveradresse
	 */
	public HTTPClientConnection(String hostname)
	{
		this.hostname = hostname;
	}
	
	/**
	 * Vorbereitung der  Anfrage an den Server.
	 * Gibt Antwort des Servers als String zurueck
	 * @param cmd
	 * @return
	 */
	public synchronized String sendRequest(String cmd) {
		
		String retV = "";
		
		try {
			url = new URL(cmd);	// URL mit Anfrage-Zeichenkette anlegen
			
			retV = nextServerRequest(cmd);	// Anfrage-Methode aufrufen
		}
		catch (MalformedURLException e) { e.printStackTrace(); } 
		
		return retV;	// Rueckgabe der Serverantwort
	}
	
	/**
	 * Sendet Anfrage an den Server und gibt Antwort aus String zurueck
	 * @param cmd
	 * @return
	 */
	private synchronized String nextServerRequest(String cmd)
	{
		StringBuffer str = new StringBuffer();	// Speichert die Serverantwort
		
		// Serververbindung herstellen & InputStream initialisieren
		try {
			con = url.openConnection();
						
			scan = new Scanner(con.getInputStream());
			
		} catch (IOException e) {
//			e.printStackTrace();
		}
		
		if(scan!=null)	// Fehlermeldung durch Unterbrechung des Servers abfangen
		{
			while(scan.hasNext())
			{
				str.append(scan.nextLine());
			}
			
			scan.close();	// InputStream schliessen
		}
		
		con = null;			// Verbindungsobjekt auf null setzen

		return str.toString();	// Rueckgabe der Serverantwort
	}
	
//	public static void main(String[] arg0)
//	{
////		new HTTPBytePacketConnection("localhost");
//		new HTTPClientConnection("127.0.0.1");
//	}
}
