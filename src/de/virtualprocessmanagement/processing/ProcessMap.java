package de.virtualprocessmanagement.processing;

import java.io.File;
import java.util.ArrayList;

import de.virtualprocessmanagement.objects.MoveableSubject;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.tools.FileHandler;

public class ProcessMap {
 
	protected String[][] mapAsStringArray = null;
	
	protected ArrayList<RectShape> objectList = new ArrayList<RectShape>();
	
	public ProcessMap(String filename) {
		
		loadMap(filename);
		
		initObjectList();
		
	}
	
	private void loadMap(String filename) {
		
		FileHandler fh = new FileHandler();
		
		String[] strArr = fh.getTextLines(new File(filename));
		String[] swap = null;
		
		mapAsStringArray = new String[strArr.length][(strArr[0].split(";")).length];
		
		for(int i=0; i<strArr.length; i++) {
			
			swap = strArr[i].split(";");
			
			for(int j=0; j<swap.length; j++)
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
		
		System.out.println("entry="+entry);
		
		if(entry.equals("s"))
		{
			shape = new RectShape(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
			shape.setSubjectTyp(RectShape.STATIC_SUBJECT);
			shape.setName("STATIC_SUBJECT:x="+j+";y="+i);
			return shape;
		}
		else
			if(entry.equals("ms"))
			{
				shape = new MoveableSubject(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
				shape.setSubjectTyp(RectShape.MOVEABLE_SUBJECT);
				shape.setName("MOVEABLE_SUBJECT:x="+j+";y="+i);
				return shape;
			}
			else
				if(entry.equals("m"))
				{
					shape = new RectShape(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
					shape.setSubjectTyp(RectShape.MACHINE_WAY_SUBJECT);
					shape.setName("MACHINE_WAY_SUBJECT:x="+j+";y="+i);
					return shape;
				}
				else
					if(entry.equals("h"))
					{
						shape = new RectShape(RectShape.DEFAULT_WIDTH*(1+j), RectShape.DEFAULT_HEIGHT*(1+i), RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, j, i);
						shape.setSubjectTyp(RectShape.HUMAN_WAY_SUBJECT);
						shape.setName("HUMAN_WAY_SUBJECT:x="+j+";y="+i);
						return shape;
					}
		
//		System.out.println(shape.toString());
		
		return shape;
	}
	
	public ArrayList<RectShape> getObjectList() {
		return objectList;
	}
}
