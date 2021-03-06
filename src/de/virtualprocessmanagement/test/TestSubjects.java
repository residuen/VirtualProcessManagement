package de.virtualprocessmanagement.test;

import java.util.ArrayList;

import de.virtualprocessmanagement.objects.MoveableObject;
import de.virtualprocessmanagement.objects.RectShape;

public class TestSubjects {

	public static ArrayList<RectShape> getObjectList() {
		
		ArrayList<RectShape> al = new ArrayList<RectShape>();
		
		MoveableObject shape = null;
		
		for(int i=0; i<10; i++)
			al.add(new RectShape(RectShape.DEFAULT_WIDTH*(1+i), RectShape.DEFAULT_HEIGHT, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, i, 0));
		
		shape = new MoveableObject(RectShape.DEFAULT_WIDTH, 2.5*RectShape.DEFAULT_HEIGHT, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT, 0, 1);
		shape.setDirections(RectShape.X_DIRECTION);
		
		al.add(shape);
		
		return al;
	}
}
