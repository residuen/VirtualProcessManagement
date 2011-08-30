package de.virtualprocessmanagement.processing;

import java.awt.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPClient;
import de.virtualprocessmanagement.interfaces.ShapeHandler;
import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.tools.ServerInfos;

public class ProcessManager implements HTTPClient, ShapeHandler  {
	
	public static final int CELL_WIDTH = 25, CELL_HEIGHT = 25;
	
	private ServerClientConnectionLayer connectionLayer = null;
	
	private ShapeManager shapeManager = null;
	
	private ProcessMap processMap = null;
	
	private String data = null;

	public ProcessManager(String mapName) {
		processMap = new ProcessMap(mapName, CELL_WIDTH, CELL_HEIGHT);
		
		shapeManager = new ShapeManager(processMap);
	}

	@Override
	public void loop(String data) {
		
		this.data = data;
		
		System.out.println("ProcessManager:"+data);
		
		String[] swap = null;
		
		data = data.toLowerCase();
		
		if(data.contains("?getserverinfo"))
			dataResponseEvent(getServerInfo(data));
		else
		if(data.contains("?moveobject="))	// moveObject=objectGroup,objectId,left/up/right/down  
		{
			swap = data.substring(data.indexOf("=")+1).split(",");
			
			if(swap.length==2)
				moveObject(Integer.parseInt(swap[0]), swap[1]);
			else
				moveObject(Integer.parseInt(swap[0]), Integer.parseInt(swap[1]), swap[2]);
		}
		else
		if(data.contains("?chargeobjectbygroup="))	// moveObject=objectGroup,objectId,left/up/right/down  
		{
			swap = data.substring(data.indexOf("=")+1).split(",");

			chargeObjectByGroup(Integer.parseInt(swap[0]), Integer.parseInt(swap[1]), swap[2]);
		}
		else
		if(data.contains("?chargeobjectbyid="))	// moveObject=objectGroup,objectId,left/up/right/down  
		{
			swap = data.substring(data.indexOf("=")+1).split(",");
			
			chargeObjectById(Integer.parseInt(swap[0]), Integer.parseInt(swap[1]), swap[2]);
		}
		else
		if(data.contains("?dischargeobjectbyid="))	// moveObject=objectGroup,objectId,left/up/right/down  
		{
			swap = data.substring(data.indexOf("=")+1).split(",");
			
			dischargeObjectById(Integer.parseInt(swap[0]), swap[1]);
		}
		else
		if(data.contains("?objectinfo="))	// moveObject=objectGroup,objectId,left/up/right/down  
		{
			getObjectInfo(data);
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
		
		ServerInfos serverInfos = new ServerInfos();
		
		if(data.toLowerCase().equals("client?getserverinfo"))
		{
			return new String[] { "\nserver-home="+serverInfos.getServerHome()+";",
												   "\nserver-ip="+serverInfos.getServerIP()+";",
												   "\nserver-name="+serverInfos.getServerName()+";",
												   "\nserver-cores="+serverInfos.getServerCores() };
		}
		else		
			if(data.toLowerCase().equals("client?getserverinfoashtml"))
			{		
				return new String[] { "<html>\n<body>\nserver-home="+serverInfos.getServerHome()+"<br>",
													   "server-ip="+serverInfos.getServerIP()+"<br>",
													   "server-name="+serverInfos.getServerName()+"<br>",
													   "server-cores="+serverInfos.getServerCores()+"\n</body>\n</html>" };
		}
		
		return null;
	}

	@Override
	public void moveObject(int objectGroup, int objectMapId, String direction) {

		shapeManager.moveObject(objectGroup, objectMapId, direction);
				
		dataResponseEvent(new String[] {"server?acknowledge="+this.data+";"+true});
	}
	
	@Override
	public void moveObject(int objectId, String direction) {

		shapeManager.moveObject(objectId, direction);
				
		dataResponseEvent(new String[] {"server?acknowledge="+this.data+";"+true});
	}

	@Override
	public void chargeObjectByGroup(int objectGroup, int objectMapId, String direction) {
		
		shapeManager.chargeObjectByGroup(objectGroup, objectMapId, direction);
		
		dataResponseEvent(new String[] {"server?acknowledge="+this.data+";"+true});
	}

	@Override
	public void chargeObjectById(int objectId, int chargeId, String direction) {
		
		shapeManager.chargeObjectById(objectId, chargeId, direction);
		
		dataResponseEvent(new String[] {"server?acknowledge="+this.data+";"+true});
	}

	@Override
	public void dischargeObjectById(int lifterObjectId,  String direction) {
		
		shapeManager.dischargeObjectById(lifterObjectId, direction);
//		
		dataResponseEvent(new String[] {"server?acknowledge="+this.data+";"+true});
	}

	public void getObjectInfo(String data) {
		
		String[] swap = data.split("objectinfo=");
		String[] swap2 = null;
		String[] objects = null;
		
		ArrayList<SubjectShape> objectList = null; //processMap.getAllObjects();
		ArrayList<SubjectShape> swapList = null;
		
		if(swap[1].toLowerCase().equals("getall"))
			objectList = processMap.getAllObjects();
		else
		if(swap[1].toLowerCase().equals("getallstatic"))
			objectList = processMap.getObjectList(RectShape.STORAGE_OBJECT);
		else
		if(swap[1].toLowerCase().equals("getallmoveable"))
			objectList = processMap.getObjectList(RectShape.MOVEABLE_OBJECT);
		else
		if(swap[1].toLowerCase().equals("getallpartialmoveable"))
			objectList = processMap.getObjectList(RectShape.PARTIAL_MOVEABLE_OBJECT);
		else
		if(swap[1].toLowerCase().equals("getallcharge"))
			objectList = processMap.getObjectList(RectShape.CHARGE_OBJECT);
		else
		if(swap[1].toLowerCase().contains("getbygroup"))
		{
			swap2 = swap[1].toLowerCase().split("getbygroup:");
			
			if(swap2[1].contains(","))
			{
				swapList = new ArrayList<SubjectShape>();
				swapList.add(processMap.getObjectList(swap2[1].split(",")[0]).get(Integer.parseInt(swap2[1].split(",")[1])));
				objectList = new ArrayList<SubjectShape>(swapList);
			}
			else
				objectList = processMap.getObjectList(swap2[1]);
		}
		
		objects = new String[objectList.size()+1];
		
		objects[0] = "serveranswer?"+data+"\n";
		
		for(int i=0; i<objectList.size(); i++) {
			objects[i+1] = objectList.get(i).toString()+"\n";
		}
		
		dataResponseEvent(objects);
	}
	
	public void setVisuComponent(Component visuComponent) {
		shapeManager.setVisuComponent(visuComponent);
	}
}
