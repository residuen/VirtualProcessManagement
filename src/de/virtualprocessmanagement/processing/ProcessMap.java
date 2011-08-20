package de.virtualprocessmanagement.processing;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.virtualprocessmanagement.objects.MoveableSubject;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.tools.FileHandler;

public class ProcessMap {
 
	protected String[][] mapAsStringArray = null;
	
	protected ArrayList<RectShape> objectList = new ArrayList<RectShape>();
	
	protected HashMap<String, ArrayList<RectShape>> objectMap = new HashMap<String, ArrayList<RectShape>>();
	
	protected Shape boundary = null;
	
	protected int fieldsX = 0, fieldsY = 0, cellWidth = 0, cellHeight = 0;
	
	public ProcessMap(String filename, int cellWidth, int cellHeight) {
		
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		loadMap(filename);
		
		initObjectList();
		
	}
	
	private void loadMap(String filename) {
		
		FileHandler fh = new FileHandler();
		
		String[] strArr = fh.getTextLines(new File(filename));
		String[] swap = null;
		
		fieldsY = strArr.length;
		fieldsX = (strArr[0].split(";")).length;
		
		boundary = new Rectangle2D.Double(0, 0, cellWidth*fieldsX, cellHeight*fieldsY);
		
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
	private RectShape compileSubject(String entry, int i, int j) {
		
		RectShape shape = null;
		
//		System.out.println("entry="+entry);
		
		String typ = null;
		
		if(entry.equals("s"))
		{
			typ = Integer.toString(RectShape.STATIC_SUBJECT);
			
			if(objectMap.get(typ) == null)
				objectMap.put(typ, new ArrayList<RectShape>());
				
			shape = new RectShape(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
			shape.setSubjectTyp(RectShape.STATIC_SUBJECT);
			shape.setName("storage");
		}
		else
			if(entry.equals("ms"))
			{
				typ = Integer.toString(RectShape.MOVEABLE_SUBJECT);
				
				if(objectMap.get(typ) == null)
					objectMap.put(typ, new ArrayList<RectShape>());
					
				shape = new MoveableSubject(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
				shape.setSubjectTyp(RectShape.MOVEABLE_SUBJECT);
				shape.setName("moveable");
			}
			else
				if(entry.equals("m"))
				{
					typ = Integer.toString(RectShape.MACHINE_WAY_SUBJECT);
					
					if(objectMap.get(typ) == null)
						objectMap.put(typ, new ArrayList<RectShape>());
						
					shape = new RectShape(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
					shape.setSubjectTyp(RectShape.MACHINE_WAY_SUBJECT);
					shape.setName("machineway");
				}
				else
					if(true) // entry.equals("h"))
					{
						typ = Integer.toString(RectShape.HUMAN_WAY_SUBJECT);
						
						if(objectMap.get(typ) == null)
							objectMap.put(typ, new ArrayList<RectShape>());
							
						shape = new RectShape(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
//						shape.set
						shape.setSubjectTyp(RectShape.HUMAN_WAY_SUBJECT);
						shape.setName("humanway");
					}
		
//		System.out.println(shape.toString());
		
		shape.setId(objectMap.get(typ).size());
		objectMap.get(typ).add(shape);
		
		return shape;
	}
	
	public int getFieldsX() {
		return fieldsX;
	}
	
	public int getFieldsY() {
		return fieldsY;
	}
	
	public ArrayList<RectShape> getObjectList() {
		return objectList;
	}
	
	public Shape getBoundary() {
		return boundary;
	}


}
