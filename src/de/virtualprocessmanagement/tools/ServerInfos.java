package de.virtualprocessmanagement.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerInfos {
	
	private InetAddress inetAdress = null;
	
	public ServerInfos() {
		
		inetAdress = null;
		try { inetAdress = InetAddress.getLocalHost(); }
		catch (UnknownHostException e) { e.printStackTrace(); }
	}
	
	public String getServerIP() {
		return inetAdress.getHostAddress();
	}
	
	public String getServerName() {
		return inetAdress.getHostName();
	}
	
	public String getServerHome() {
		return System.getProperty("user.home");
	}
	
	public int getServerCores() {
		return Runtime.getRuntime().availableProcessors();
	}
	
}
