package de.virtualprocessmanagement.interfaces;

import java.io.DataOutputStream;
import java.net.ServerSocket;

import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;

public interface HTTPServer {

	public ServerSocket getServersocket();
	public void setSimulationController( ServerClientConnectionLayer simulationController);
	public String getRequestPath();
	public String getRequestText();
	public void sendResponseText(String text, DataOutputStream output);
	public void interrupt();
}
