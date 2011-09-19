package de.virtualprocessmanagement.interfaces;

public interface HTTPClient {

	public void loop(String data);
	public void dataResponseEvent(String[] data);
	public void sendNextRequest(String data);
	public void setHostAdress(String host);
}
