package de.virtualprocessmanagement.objects;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import de.virtualprocessmanagement.interfaces.SubjectShape;

public class RectShape extends Rectangle2D.Double implements SubjectShape {

	public static final int STATIC_SUBJECT = 0;
	public static final int PARTIAL_MOVEABLE_SUBJECT = 1;
	public static final int MOVEABLE_SUBJECT = 2;
	public static final int ROBOT = 3;
	public static final int MACHINE_WAY_SUBJECT = 4;
	public static final int HUMAN_WAY_SUBJECT = 5;
	
	public static int NO_DIRECTION = STATIC_SUBJECT;
	public static int X_DIRECTION = 1;
	public static int Y_DIRECTION = 2;
	public static int Z_DIRECTION = 3;
	
	public static int LEFT = 0;
	public static int UP = 1;
	public static int RIGHT = 2;
	public static int DOWN = 3;
	
	public static final double DEFAULT_WIDTH = 25, DEFAULT_HEIGHT = 25;
	
	protected int id = 0;
	
	protected String name = null;

	protected int x_index = 0, y_index = 0;
	
	protected int subjectTyp = STATIC_SUBJECT;
	protected int directions = NO_DIRECTION;
	
	protected double width = DEFAULT_WIDTH;
	protected double height = DEFAULT_HEIGHT;
	
	protected boolean showId = true;

	protected Color fillColor = Color.RED, collisionsColor = Color.CYAN, frameColor = Color.BLACK;

	public RectShape() {
		super();
	}

	public RectShape(double arg0, double arg1, int x_index, int y_index) {
		super();
		super.setRect(arg0, arg1, arg0+DEFAULT_WIDTH, arg1+DEFAULT_HEIGHT);
		
		this.x_index = x_index;
		this.y_index = y_index;
	}

	public RectShape(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {
		super(arg0, arg1, arg2, arg3);
				
		this.x_index = x_index;
		this.y_index = y_index;
		
		width = arg2;
		height = arg3;
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

	public int getSubjectTyp() {
		return subjectTyp;
	}

	public String getSubjectTypAsString() {
		return Integer.toString(subjectTyp);
	}

	public void setSubjectTyp(int subjectTyp) {
		this.subjectTyp = subjectTyp;
		
//		System.out.println("subjectTyp="+subjectTyp);
		
		switch(subjectTyp) {
			case STATIC_SUBJECT:
				fillColor = Color.RED;
				break;
		
			case PARTIAL_MOVEABLE_SUBJECT:
				fillColor = Color.GREEN;
				break;
		
			case MOVEABLE_SUBJECT:
				fillColor = Color.LIGHT_GRAY;
				break;
			
			case MACHINE_WAY_SUBJECT:
				fillColor = Color.YELLOW;
				break;
			
			case HUMAN_WAY_SUBJECT:
				fillColor = Color.WHITE;
				frameColor = Color.LIGHT_GRAY;
				break;

//			default:
//				fillColor = Color.RED;
//				break;
		}
	}

	public int getDirections() {
		return directions;
	}

	public void setDirections(int directions) {
		this.directions = directions;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public int getX_index() {
		return x_index;
	}

	public int getY_index() {
		return y_index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isShowId() {
		return showId;
	}

	public void setShowId(boolean showId) {
		this.showId = showId;
	}


	
	@Override
	public String toString() {
		return "[name="+name+",subjectTyp="+subjectTyp+",x_index="+x_index+",y_index="+y_index+"]";
	}

}
