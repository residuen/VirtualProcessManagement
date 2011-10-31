package de.virtualprocessmanagement.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Klasse zum auslesen von Server/Netzwerkinformationen
 * beim ausfuehrenden Rechner
 * @author bettray
 *
 */
public class ServerInfos {
	
	private InetAddress inetAdress = null;
	
	public ServerInfos() {
		
		inetAdress = null;
		try { inetAdress = InetAddress.getLocalHost(); }
		catch (UnknownHostException e) { e.printStackTrace(); }
	}
	
	/**
	 * IP-Adresse zurueckliefern
	 * @return
	 */
	public String getServerIP() {
		return inetAdress.getHostAddress();
	}
	
	/**
	 * Rechnername zurueckliefern
	 * @return
	 */
	public String getServerName() {
		return inetAdress.getHostName();
	}
	
	/**
	 * Home-Verzeichnis des Nutzers/Servers zurueckgeben
	 * @return
	 */
	public String getServerHome() {
		return System.getProperty("user.home");
	}
	
	/**
	 * Anzahl der CPU-Kerne zurueckgeben
	 * @return
	 */
	public int getServerCores() {
		return Runtime.getRuntime().availableProcessors();
	}
	
}
