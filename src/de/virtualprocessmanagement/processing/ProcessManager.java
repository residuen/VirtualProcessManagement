package de.virtualprocessmanagement.processing;

import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPClient;

public class ProcessManager implements HTTPClient {
	
	private ServerClientConnectionLayer connectionLayer = null;

	public ProcessManager(ServerClientConnectionLayer connectionLayer) {
		this.connectionLayer = connectionLayer;
	}
	
	@Override
	public void dataRequestEvent(String data) {
		
		String[] str = new String[3];
		for(int i=0; i<3; i++)
		{
			str[i] = "{id="+i+";x="+(i*10)+";x="+(i*10)+"}\n";
		}
		
		dataResponseEvent(str);

	}

	@Override
	public void dataResponseEvent(String[] data) {
		connectionLayer.clientResponse(data);
	}

	@Override
	public void sendNextRequest(String data) {
		// TODO Auto-generated method stub
		
	}

}
