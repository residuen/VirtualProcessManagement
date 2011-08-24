package de.virtualprocessmanagement.objects;

import java.awt.Color;

public class MoveableObject extends RectShape {

	public MoveableObject() {
		super();
	}

	public MoveableObject(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {
		super(arg0, arg1, arg2, arg3, x_index, y_index);
		
		super.setGroup(RectShape.MOVEABLE_OBJECT);
		
		fillColor = Color.LIGHT_GRAY;
	}

	public MoveableObject(double arg0, double arg1, int x_index, int y_index) {
		super(arg0, arg1, x_index, y_index);
		
		super.setGroup(RectShape.MOVEABLE_OBJECT);
		
		fillColor = Color.LIGHT_GRAY;
	}

}
