package de.virtualprocessmanagement.interfaces;

public interface HTTPClient {

	public void dataRequestEvent(String data);
	public void dataResponseEvent(String data);
	public void sendNextRequest(String data);
}
