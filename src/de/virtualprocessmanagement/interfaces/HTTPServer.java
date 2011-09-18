package de.virtualprocessmanagement.interfaces;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;

public interface HTTPServer {

	public ServerSocket getServersocket();
//	public void setConnectionLayer( ServerClientConnectionLayer connectionLayer);
	public String getRequestPath();
	public String getRequestText();
	public void sendResponseText(String[] text, OutputStreamWriter output);
	public void interrupt();
}
