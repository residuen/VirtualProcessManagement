package de.virtualprocessmanagement.objects;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class RectShape extends Rectangle2D.Double {

	public static int STATIC_SUBJECT = 0;
	public static int PARTIAL_STATIC_SUBJECT = 1;
	public static int MOVEABLE_SUBJECT = 2;
	
	public static final double DEFAULT_WIDTH = 25, DEFAULT_HEIGHT = 25;

	protected int subjectTyp = STATIC_SUBJECT;
	

	protected Color fillColor = Color.RED, collisionsColor = Color.CYAN, frameColor = Color.BLACK;

	public RectShape() {
		super();
	}

	public RectShape(double arg0, double arg1) {
		super();
		super.setRect(arg0, arg1, arg0+DEFAULT_WIDTH, arg1+DEFAULT_HEIGHT);
	}

	public RectShape(double arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getCollisionsColor() {
		return collisionsColor;
	}

	public Color getFrameColor() {
		return frameColor;
	}

}
