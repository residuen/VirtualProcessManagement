package de.virtualprocessmanagement.processing;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.ChargeObject;
import de.virtualprocessmanagement.objects.ForkliftObject;
import de.virtualprocessmanagement.objects.MoveableObject;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.objects.StorageObject;
import de.virtualprocessmanagement.tools.FileHandler;

public class ProcessMap {
 
	public static final int OFFSET_X = 25;
	public static final int OFFSET_Y = 25;
	
	protected String[][] mapAsStringArray = null;
	
	protected ArrayList<SubjectShape> objectList = new ArrayList<SubjectShape>();
	
	protected HashMap<String, ArrayList<SubjectShape>> objectMap = new HashMap<String, ArrayList<SubjectShape>>();
	
	protected Shape boundary = null;
	
	protected int fieldsX = 0, fieldsY = 0, cellWidth = 0, cellHeight = 0;
	
	public ProcessMap(String filename, int cellWidth, int cellHeight) {
		
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		objectList.clear();
		objectMap.clear();
		
		loadMap(filename);
		
		initObjectList();
		
	}
	
	private void loadMap(String filename) {
		
		FileHandler fh = new FileHandler();
		
		String[] strArr = fh.getTextLines(new File(filename));
		String[] swap = null;
		
		fieldsY = strArr.length;
		fieldsX = (strArr[0].split(";")).length;
		
//		System.out.println(cellWidth+"*"+fieldsX+"="+(cellWidth*fieldsX));
		
		boundary = new Rectangle2D.Double(OFFSET_X, OFFSET_Y, cellWidth*fieldsX, cellHeight*fieldsY);
		
		mapAsStringArray = new String[fieldsY][fieldsX];
		
		for(int i=0; i<fieldsY; i++) {
			
			swap = strArr[i].split(";");
			
			for(int j=0; j<fieldsX; j++)
				mapAsStringArray[i][j] = swap[j].trim();
		}
		
//		for(String[] arr : mapAsStringArray) {
//			for(String str : arr) 
//				System.out.print(str+" ");
//		
//			System.out.println("");
//		}
	}
	
	private void initObjectList() {
		
		String[] swap = null;
		
		RectShape shape = null;
		
		for(int i=0; i<mapAsStringArray.length; i++) {
			
			swap = mapAsStringArray[i];
			
			for(int j=0; j<swap.length; j++)
			{
				shape = compileSubjects(mapAsStringArray[i][j], i, j);
				
				if(shape != null)
					objectList.add(shape);
			}
		}
		
		for(int i=0; i<objectList.size(); i++)
		{
			objectList.get(i).setId(i);
			addToObjectMap(Integer.toString(objectList.get(i).getGroup()), objectList.get(i));
		}
		
//		return al;
	}
	
	/**
	 * Auswerten der MAP-Felder
	 */
	private RectShape compileSubjects(String entry, int i, int j) {
		
		RectShape shape = null;
		
		String[] swap = null;
		
		if(!entry.contains(","))
			objectList.add(compileSubject(entry, i, j));
		else
		{
			swap = entry.split(",");
			
			for(String s : swap)
				objectList.add(compileSubject(s, i, j));
		}
		
//		System.out.println(shape.toString());
		
		return shape;
		
	}
	
	/**
	 * Erzeugen der einzelnen Shape-Objekte
	 * @param entry
	 * @param i
	 * @param j
	 * @return
	 */
	private SubjectShape compileSubject(String entry, int i, int j) {
		
		SubjectShape shape = null;
		
//		System.out.println("entry="+entry);
		
//		String typ = null;
		
		if(entry.equals("s"))
		{
//			typ = Integer.toString(RectShape.STORAGE_OBJECT);
			
			shape = new StorageObject(OFFSET_X + RectShape.DEFAULT_WIDTH*j, OFFSET_Y + RectShape.DEFAULT_HEIGHT*i, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
			shape.setGroup(RectShape.STORAGE_OBJECT);
			shape.setName("storage");
		}
		else
			if(entry.equals("mo"))
			{
//				typ = Integer.toString(RectShape.MOVEABLE_OBJECT);
				
				shape = new MoveableObject(OFFSET_X + RectShape.DEFAULT_WIDTH*j, OFFSET_Y + RectShape.DEFAULT_HEIGHT*i, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
				shape.setGroup(RectShape.MOVEABLE_OBJECT);
				shape.setName("moveable");
			}
			else
				if(entry.equals("fl"))
				{
					shape = new ForkliftObject(OFFSET_X + RectShape.DEFAULT_WIDTH*j, OFFSET_Y + RectShape.DEFAULT_HEIGHT*i, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
					shape.setGroup(RectShape.FORKLIFT);
					shape.setName("forklift");
				}
				else
					if(entry.equals("cl"))
					{
						shape = new ChargeObject(OFFSET_X + RectShape.DEFAULT_WIDTH*j, OFFSET_Y + RectShape.DEFAULT_HEIGHT*i, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
						shape.setGroup(RectShape.CHARGE_OBJECT);
						shape.setName("charge");
					}
					else
						if(entry.equals("m"))
						{
							shape = new RectShape(OFFSET_X + RectShape.DEFAULT_WIDTH*j, OFFSET_Y + RectShape.DEFAULT_HEIGHT*i, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
							shape.setGroup(RectShape.MACHINE_WAY_OBJECT);
							shape.setName("machineway");
						}
						else
						{
							shape = new RectShape(OFFSET_X + RectShape.DEFAULT_WIDTH*j, OFFSET_Y + RectShape.DEFAULT_HEIGHT*i, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
							shape.setGroup(RectShape.HUMAN_WAY_OBJECT);
							shape.setName("humanway");
						}
		
		return shape;
	}
	
	/**
	 * Speichern der Objekte aus der ArrayList in einer HashMap
	 * @param typ
	 * @param shape
	 */
	private void addToObjectMap(String typ, SubjectShape shape) {
		
		// Testen, ob Object-Map fuer Objektgruppe vorhanden ist, wenn nicht wird sie angelegt 
		if(objectMap.get(typ) == null)
			objectMap.put(typ, new ArrayList<SubjectShape>());
		
		shape.setMapId(objectMap.get(typ).size());
		objectMap.get(typ).add(shape);

	}
	
	public int getFieldsX() {
		return fieldsX;
	}
	
	public int getFieldsY() {
		return fieldsY;
	}
	
	/**
	 * Liefert alle Shapes zurueck
	 * @return
	 */
	public synchronized ArrayList<SubjectShape> getAllObjects() {
		return objectList;
	}
	
	/**
	 * Liefert nur die Shapes der gewuenschten Gruppe zurueck
	 * @param objectGroup
	 * @return
	 */
	public synchronized ArrayList<SubjectShape> getObjectList(int objectGroup) {
		return objectMap.get(Integer.toString(objectGroup));
	}
	
	/**
	 * Liefert nur die Shapes der gewuenschten Gruppe zurueck
	 * @param objectGroup
	 * @return
	 */
	public synchronized ArrayList<SubjectShape> getObjectList(String objectGroup) {
		return objectMap.get(objectGroup);
	}

	public synchronized Shape getBoundary() {
		return boundary;
	}


}
