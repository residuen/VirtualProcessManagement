package de.virtualprocessmanagement.objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.processing.ShapeMover;

public class SimplePath extends MainObject implements SubjectShape {

	private GeneralPath path = new GeneralPath();
	
	private double offsetX = 0;
	private double offsetY = 0;

	public SimplePath() {
//		super();
	}

	public SimplePath(double arg0, double arg1, int x_index, int y_index) {
		
		init(arg0, arg1, DEFAULT_WIDTH, DEFAULT_HEIGHT, x_index, y_index);
	}

	public SimplePath(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {
		
		init(arg0, arg1, arg2, arg3, x_index, y_index);
	}
	
	private void init(double x, double y, double w, double h, int x_index, int y_index) {
		
		this.x = x + offsetX;
		this.y = y + offsetY;
		
		this.x_index = x_index;
		this.y_index = y_index;
		
		this.width = w;
		this.height = h;
		
		initPath();
	}
	
	private void initPath() {
		
		path.reset();
		
		// Path zusammensetzen
		path.moveTo(x, y);
		path.lineTo(x+width, y);
		path.lineTo(x+width, y+height);
		path.lineTo(x, y+height);
		path.lineTo(x, y);
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

	@Override
	public void setX_index(int x_index) {
		this.x_index = x_index;
	}

	@Override
	public void setY_index(int y_index) {
		this.y_index = y_index;
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

	@Override
	public double getX() {
		return x; //vehicle.getBounds2D().getX();
	}

	@Override
	public double getY() {
		return y; //vehicle.getBounds2D().getY();
	}

	@Override
	public void setRect(double x, double y, double width, double height) {
		
//		System.out.println("PathShape: setRect-> x="+x+" y="+y+" width="+width+" height"+height);

		this.x = x + offsetX;
		this.y = y + offsetY;

//		this.x += offsetX;
//		this.y += offsetY;

		initPath();
	}

	@Override
	public boolean hasMoveableAdditionalShape() {
		return false;
	}

	@Override
	public SubjectShape getAdditionalShape() {
		return null;
	}

	@Override
	public void chargeLoad(int direction, int sleepTime, Component component) {
		
		if(!isShapeLocked())
		{
			lockShape();
			ShapeMover shapeMover = new ShapeMover(this, direction, sleepTime, component);
			shapeMover.start();
			try { shapeMover.join(); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}	}

	@Override
	public void dischargeLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAdditionalShape(SubjectShape shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chargeLoad() {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		path.reset();
	}

	public void moveTo(double arg0, double arg1) {
		path.moveTo(arg0, arg1);
	}

	public void lineTo(double arg0, double arg1) {
		path.lineTo(arg0, arg1);
	}

	public void closePath() {
		path.closePath();
	}

	public void append(SimplePath s, boolean b) {
		path.append(s, b);
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
		
		initPath();
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;

		initPath();
	}

	@Override
	public void updateObject() {
		// TODO Auto-generated method stub
		
	}

}