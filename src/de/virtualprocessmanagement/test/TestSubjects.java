package de.virtualprocessmanagement.test;

import java.util.ArrayList;

import de.virtualprocessmanagement.objects.RectShape;

public class TestSubjects {

	public static ArrayList<RectShape> getObjectList() {
		
		ArrayList<RectShape> al = new ArrayList<RectShape>();
		
		for(int i=0; i<10; i++)
			al.add(new RectShape(RectShape.DEFAULT_WIDTH*(1+i), RectShape.DEFAULT_HEIGHT, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT));
		
		return al;
	}
}
