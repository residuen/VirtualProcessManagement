package de.virtualprocessmanagement.processing;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPClient;

public class ProcessManager implements HTTPClient {
	
	public static final int CELL_WIDTH = 30, CELL_HEIGHT = 30;
	
	private ServerClientConnectionLayer connectionLayer = null;
	
	private ShapeManager shapeManager = null;
	
	private ProcessMap processMap = null;

	public ProcessManager(String mapName) {
		processMap = new ProcessMap(mapName, CELL_WIDTH, CELL_HEIGHT);
		
		shapeManager = new ShapeManager(processMap);
	}

	@Override
	public void loop(String data) {
		
		String[] str; //  = new String[] {"Client asked the server: "+data };
		
		if(data.contains("?getserverinfo"))
			dataResponseEvent(getServerInfo(data));

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
	
	public String[] getServerInfo(String data) {
		if(data.toLowerCase().equals("client?getserverinfo"))
		{
			InetAddress inetAdress = null;
			try { inetAdress = InetAddress.getLocalHost(); }
			catch (UnknownHostException e) { e.printStackTrace(); }
			
			return new String[] { "\nserver-home="+System.getProperty("user.home")+";",
												   "\nserver-ip="+inetAdress.getHostAddress()+";",
												   "\nserver-name="+inetAdress.getHostName()+";",
												   "\nserver-cores="+Runtime.getRuntime().availableProcessors() };
		}
		else		
			if(data.toLowerCase().equals("client?getserverinfoashtml"))
			{
				InetAddress inetAdress = null;
				try { inetAdress = InetAddress.getLocalHost(); }
				catch (UnknownHostException e) { e.printStackTrace(); }
			
				return new String[] { "<html>\n<body>\nserver-home="+System.getProperty("user.home")+"<br>",
													   "server-ip="+inetAdress.getHostAddress()+"<br>",
													   "server-name="+inetAdress.getHostName()+"<br>",
													   "server-cores="+Runtime.getRuntime().availableProcessors()+"\n</body>\n</html>" };
		}
		
		return null;
	}

}
