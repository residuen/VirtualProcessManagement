package de.virtualprocessmanagement.interfaces;

/**
 * Interface fuer Netzwerk-Clients
 * zum einfachen Senden von Server-Requests
 * @author bettray
 *
 */
public interface HTTPClient {

	public void loop(String data);
	public void dataResponseEvent(String[] data);
	public void sendNextRequest(String data);
	public void setHostAdress(String host);
}
