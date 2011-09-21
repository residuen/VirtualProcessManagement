package de.virtualprocessmanagement.objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.virtualprocessmanagement.interfaces.SubjectShape;

public class RectShape extends MainObject implements SubjectShape {

	Rectangle2D.Double shape = new Rectangle2D.Double();
	
	public RectShape() {
		super();
	}

	public RectShape(double arg0, double arg1, int x_index, int y_index) {

		shape.setRect(arg0, arg1, arg0+DEFAULT_WIDTH, arg1+DEFAULT_HEIGHT);
		
		this.x_index = x_index;
		this.y_index = y_index;
	}

	public RectShape(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {

		shape.setRect(arg0, arg1, arg2, arg3);
		
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
				
			case CHARGE_OBJECT:
				fillColor = Color.BLUE;
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
		return "[id="+id+",group="+group+",name="+name+",x="+shape.getX()+",y="+shape.getY()+",groupId="+groupId+",x_index="+x_index+",y_index="+y_index+"]";
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
	public boolean hasMoveableAdditionalShape() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SubjectShape getAdditionalShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAdditionalShape(SubjectShape shape) { }

//	@Override
//	public void chargeLoad() { }

	@Override
	public void dischargeLoad(int direction, Component component) { }

	@Override
	public boolean contains(Point2D p) {

		return shape.contains(p);
	}

	@Override
	public boolean contains(Rectangle2D r) {

		return shape.contains(r);
	}

	@Override
	public boolean contains(double x, double y) {

		return shape.contains(x, y);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {

		return shape.contains(x, y, w, h);
	}

	@Override
	public Rectangle getBounds() {

		return shape.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {

		return shape.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		
		return shape.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {

		return shape.getPathIterator(at, flatness);
	}

	@Override
	public boolean intersects(Rectangle2D r) {

		return shape.intersects(r);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {

		return shape.intersects(x, y, w, h);
	}

	@Override
	public double getCenterX() {

		return shape.getCenterX();
	}

	@Override
	public double getCenterY() {

		return shape.getCenterY();
	}

	@Override
	public double getX() {

		return shape.getX();
	}

	@Override
	public double getY() {

		return shape.getY();
	}

	@Override
	public void setRect(double x, double y, double width, double height) {
		
//		final Lock lock = new ReentrantLock();
//
//		lock.lock();
		
		shape.setRect(x, y, width, height);
		
//		lock.unlock();
	}

	@Override
	public void setX_index(int x_index) {
		this.x_index = x_index;
	}

	@Override
	public void setY_index(int y_index) {
		this.y_index = y_index;
	}

	@Override
	public void updateObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}

}
