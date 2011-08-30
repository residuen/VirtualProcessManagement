package de.virtualprocessmanagement.interfaces;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;

public interface HTTPClient {

	public void loop(String data);
	public void dataResponseEvent(String[] data);
	public void sendNextRequest(String data);
	public void setConnectionLayer(ServerClientConnectionLayer connectionLayer);
	public void setHostAdress(String host);
}
