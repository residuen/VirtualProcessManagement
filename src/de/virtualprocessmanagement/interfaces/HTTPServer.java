package de.virtualprocessmanagement.interfaces;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;

public interface HTTPServer {

	public ServerSocket getServersocket();
	public void setSimulationController( ServerClientConnectionLayer simulationController);
	public String getRequestPath();
	public String getRequestText();
	public void sendResponseText(String[] text, OutputStreamWriter output);
	public void interrupt();
}
