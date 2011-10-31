package de.virtualprocessmanagement.visu;

import java.awt.Component;
import java.util.ArrayList;

import de.virtualprocessmanagement.connection.HTTPClientConnection;
import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.objects.StorageObject;
import de.virtualprocessmanagement.processing.ProcessMap;

/**
 * holt sich die objektinformationen und erstellt daraus die Shapes.
 * DIe Shapes werden in einem Festen Interwall aktualisiert.
 * @author bettray
 */
public class IndependenceObjectReader extends Thread {
	
	private HTTPClientConnection clientConnection = null;
	
	// 2 ArrayLists, zum Puffern und speichern der Shape-Objekte 
	private ArrayList<SubjectShape> objectList = null;
	private ArrayList<SubjectShape> swapObjects = new ArrayList<SubjectShape>();
	
	private String host = null;
	
	// Intervall fuer Serveranfragen
	private int sleepTime = 55;
	
	// Die JPanel-Componente in der gezeichnet wird
	private Component component = null;
	
	public IndependenceObjectReader(String host, ArrayList<SubjectShape> objectList, Component component) {
		
		this.host = host;
		this.objectList = objectList;
		this.component = component;
		
		clientConnection = new HTTPClientConnection(); //host);
	}
	
	/**
	 * Objektinfos auslesen
	 * und Shape-Objekte erzeugen
	 */
	public void getObjectsFromServer()
	{
		// Alle Objektinfos vom Server holen
		String[] answer = clientConnection.sendRequest("http://"+host+"/client?objectinfo=getall").split("\\[");

		// Variablen fuer die einzelnen Objekt-EIgenschaften
		String str[], s;
		String name;
		int id, groupId, x_index, y_index; // , group;
		double x, y;
		
		objectList.clear();
		swapObjects.clear();
		
		// Infos vom Server zerlegen und
		// in Objekt umwandeln
		for(int i=1; i<answer.length; i++)
		{
			s = answer[i].replace("]", "");
			str = s.split(",");
			
			id = Integer.parseInt(str[0].split("=")[1]);
//			group = Integer.parseInt(str[1].split("=")[1]);
			groupId = Integer.parseInt(str[5].split("=")[1]);
			x_index = Integer.parseInt(str[6].split("=")[1]);
			y_index = Integer.parseInt(str[7].split("=")[1]);
			
			name = str[2].split("=")[1];
			
			x = Double.parseDouble(str[3].split("=")[1]);
			y = Double.parseDouble(str[4].split("=")[1]);
			
			// Shape erzeugen
			RectShape shape = new RectShape(x, y, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, x_index, y_index);
			shape.setGroup(groupId);
			shape.setId(id);
			shape.setName(name);
			
			// Statische Objekte erzeugen
			if(groupId==3 || groupId==4 || groupId==5 || groupId==6 || groupId==7 || groupId==8)
				swapObjects.add(shape);
			else	// Bewegliche Objekte erzeugen
				objectList.add(shape);
		}
		
		// Bewegliche Objekte hinter statischen Objekte anordnen
		if(swapObjects.size() > 0)
			objectList.addAll(swapObjects);
		
		component.repaint();
	}
	
	public void run() {
		
		// Do it Baby
		while(!isInterrupted()) {
			
			getObjectsFromServer();
			
			try { sleep(sleepTime); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}
