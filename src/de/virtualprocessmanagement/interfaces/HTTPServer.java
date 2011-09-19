package de.virtualprocessmanagement.interfaces;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;

/**
 * Interface fuer Netzwerk-Server
 * zum einfachen Handlen von Clientanfragen
 * @author bettray
 *
 */
public interface HTTPServer {

	public ServerSocket getServersocket();
	public String getRequestPath();
	public String getRequestText();
	public void sendResponseText(String[] text, OutputStreamWriter output);
	public void interrupt();
}
