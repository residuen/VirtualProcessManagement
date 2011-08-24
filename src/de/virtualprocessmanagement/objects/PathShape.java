package de.virtualprocessmanagement.objects;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;

import de.virtualprocessmanagement.interfaces.SubjectShape;

public class PathShape implements SubjectShape {

	// nicht bewegliche shapes
	public static final int STORAGE_OBJECT = 0;
	public static final int MACHINE_WAY_OBJECT = 4;
	public static final int HUMAN_WAY_OBJECT = 5;

	// (teil)bewegliche Shapes
	public static final int PARTIAL_MOVEABLE_OBJECT = 6;
	public static final int MOVEABLE_OBJECT = 7;
	public static final int FORKLIFT = 8;
	public static final int ROBOT = 9;
	
	public static final int[] staticShapeKeysNumbers = new int[] { STORAGE_OBJECT, MACHINE_WAY_OBJECT, HUMAN_WAY_OBJECT };
	public static final int[] moveableShapeKeysNumbers = new int[]{ PARTIAL_MOVEABLE_OBJECT, MOVEABLE_OBJECT, FORKLIFT, ROBOT };

	public static final String[] staticShapeKeys = new String[] { Integer.toString(STORAGE_OBJECT),
																  Integer.toString(MACHINE_WAY_OBJECT),
																  Integer.toString(HUMAN_WAY_OBJECT) };
	
	public static final String[] moveableShapeKeys = new String[]{ Integer.toString(PARTIAL_MOVEABLE_OBJECT),
															  	   Integer.toString(MOVEABLE_OBJECT),
															  	   Integer.toString(FORKLIFT),
																   Integer.toString(ROBOT) };
	
	public static int NO_DIRECTION = STORAGE_OBJECT;
	public static int X_DIRECTION = 1;
	public static int Y_DIRECTION = 2;
	public static int Z_DIRECTION = 3;
	
	public static int LEFT = 0;
	public static int UP = 1;
	public static int RIGHT = 2;
	public static int DOWN = 3;
	
	public static final double DEFAULT_WIDTH = 25, DEFAULT_HEIGHT = 25;
	
	protected int id = 0;
	
	protected int group = 0;
	
	protected String name = null;

	protected int x_index = 0, y_index = 0;
	
	protected int groupId = STORAGE_OBJECT;
	protected int directions = NO_DIRECTION;
	
	protected double width = DEFAULT_WIDTH;
	protected double height = DEFAULT_HEIGHT;
	
	protected boolean showId = true;
	
	protected boolean lock = false;

	protected Color fillColor = Color.RED, collisionsColor = Color.CYAN, frameColor = Color.BLACK;
	
	private GeneralPath path = null;

	public PathShape() {
		super();
	}

	public PathShape(double arg0, double arg1, int x_index, int y_index) {
		
		init(arg0, arg1, DEFAULT_WIDTH, DEFAULT_HEIGHT, x_index, y_index);
	}

	public PathShape(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {
		
		init(arg0, arg1, arg2, arg3, x_index, y_index);
	}
	
	private void init(double x, double y, double w, double h, int x_index, int y_index) {
		
		this.x_index = x_index;
		this.y_index = y_index;
		
		width = w;
		height = h;
		
		path = new GeneralPath();
		
		// Bewegliche Teile des Staplers: Fahrzeug
		path.moveTo(x, y);
		path.lineTo(x+w, y);
		path.lineTo(x+w, y+h);
		path.lineTo(x, y+h);
		path.lineTo(x, y);
		path.closePath();
		
		// Ausfahrbare Teile des Staplers: Linke Gabel
		path.moveTo(x, y);
		path.lineTo(x+0.333*w, y);
		path.lineTo(x+0.333*w, y+h);
		path.lineTo(x, y+h);
		path.lineTo(x, y);

		// Ausfahrbare Teile des Staplers: Linke Gabel
		path.moveTo(x+w - 0.333*w, y);
		path.lineTo(x+w, y);
		path.lineTo(x+w, y+h);
		path.lineTo(x+w - 0.333*w, y+h);
		path.lineTo(x+w - 0.333*w, y);
		path.closePath();
		
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

	public int getGroup() {
		return groupId;
	}

	public String getGroupAsString() {
		return Integer.toString(groupId);
	}

	public void setGroup(int subjectTyp) {
		this.groupId = subjectTyp;
		
//		System.out.println("subjectTyp="+subjectTyp);
		
		switch(subjectTyp) {
			case STORAGE_OBJECT:
				fillColor = Color.RED;
				break;
		
			case PARTIAL_MOVEABLE_OBJECT:
				fillColor = Color.GREEN;
				break;
		
			case MOVEABLE_OBJECT:
				fillColor = Color.LIGHT_GRAY;
				break;
			
			case MACHINE_WAY_OBJECT:
				fillColor = Color.YELLOW;
				break;
			
			case HUMAN_WAY_OBJECT:
				fillColor = Color.WHITE;
				frameColor = Color.LIGHT_GRAY;
				break;

			case FORKLIFT:
				fillColor = Color.GREEN;
				break;

			case ROBOT:
				fillColor = Color.ORANGE;
				break;
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
	
	public int getMapId() {
		return group;
	}

	public void setMapId(int mapId) {
		this.group = mapId;
	}
	
	public boolean isShowId() {
		return showId;
	}

	public void setShowId(boolean showId) {
		this.showId = showId;
	}


	
	@Override
	public String toString() {
		return "[id="+id+",group="+group+",name="+name+",groupId="+groupId+",x_index="+x_index+",y_index="+y_index+"]";
	}

	@Override
	public void lockShape() {
		lock = true;
	}

	@Override
	public void unlockShape() {
		lock = false;
	}

	@Override
	public boolean isShapeLocked() {
		return lock;
	}

	@Override
	public boolean contains(Point2D arg0) {
		return path.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return path.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return path.contains(arg0, arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return path.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		return path.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return path.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		return path.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		return path.getPathIterator(arg0, arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		return path.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		return path.intersects(arg0, arg1, arg2, arg3);
	}

	@Override
	public double getCenterX() {
		return path.getBounds2D().getCenterX();
	}

	@Override
	public double getCenterY() {
		return path.getBounds2D().getCenterY();
	}

}
