package de.virtualprocessmanagement.processing;

import java.awt.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;

import de.virtualprocessmanagement.controller.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPClient;
import de.virtualprocessmanagement.interfaces.ShapeHandler;

public class ProcessManager implements HTTPClient, ShapeHandler  {
	
	public static final int CELL_WIDTH = 25, CELL_HEIGHT = 25;
	
	private ServerClientConnectionLayer connectionLayer = null;
	
	private ShapeManager shapeManager = null;
	
	private ProcessMap processMap = null;

	public ProcessManager(String mapName) {
		processMap = new ProcessMap(mapName, CELL_WIDTH, CELL_HEIGHT);
		
		shapeManager = new ShapeManager(processMap);
	}

	@Override
	public void loop(String data) {
		
		String[] swap = null;
		
		data = data.toLowerCase();
		
		if(data.contains("?getserverinfo"))
			dataResponseEvent(getServerInfo(data));
		else
		if(data.contains("?moveobject="))	// moveObject=objectGroup,objectId,left/up/right/down  
		{
//			System.out.println("ProcessManager:"+data);
			swap = data.substring(data.indexOf("=")+1).split(",");
			
//			for( String g : swap)
//				System.out.println(g);
			
			moveObject(Integer.parseInt(swap[0]),
					   Integer.parseInt(swap[1]),
					   swap[2]);
		}
			

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

	@Override
	public void moveObject(int objectGroup, int objectId, String direction) {

		shapeManager.moveObject(objectGroup, objectId, direction);
				
		dataResponseEvent(new String[] {"server?acknowledge="+objectGroup+","+objectId+","+direction+";"+true});

	}
	
	public void setVisuComponent(Component visuComponent) {
		shapeManager.setVisuComponent(visuComponent);
	}


}
