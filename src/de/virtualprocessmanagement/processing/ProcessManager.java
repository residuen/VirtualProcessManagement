package de.virtualprocessmanagement.processing;

import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPClient;

public class ProcessManager implements HTTPClient {
	
	private ServerClientConnectionLayer connectionLayer = null;
	
	private ProcessMap processMap = null;

//	public ProcessManager(ServerClientConnectionLayer connectionLayer, String mapName) {
//		this.connectionLayer = connectionLayer;
//		
//		processMap = new ProcessMap(mapName);
//	}
	
	public ProcessManager(String mapName) {
		processMap = new ProcessMap(mapName);
	}

	@Override
	public void loop(String data) {
		
		String[] str = new String[] {"Client asked the server: "+data };
//		String[] str = new String[3];
//		for(int i=0; i<3; i++)
//		{
//			str[i] = "{id="+i+";x="+(i*10)+";x="+(i*10)+"}\n";
//		}
		
		dataResponseEvent(str);

	}

	public ProcessMap getProcessMap() {
		return processMap;
	}

	@Override
	public void dataResponseEvent(String[] data) {
		connectionLayer.clientResponse(data);
	}

	@Override
	public void sendNextRequest(String data) {
		// TODO Auto-generated method stub
		
	}

	public void setConnectionLayer(ServerClientConnectionLayer connectionLayer) {
		this.connectionLayer = connectionLayer;
	}

}
