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
import de.virtualprocessmanagement.processing.ForkMover;
import de.virtualprocessmanagement.processing.ShapeMover;

public class PathLifterShape extends MainObject implements SubjectShape {
	
	protected double x_vehicle = 0;
	protected double y_vehicle = 0;
	protected double x_fork = 0;
	protected double y_fork = 0;
	
	protected ExtendedPoint forkOffset = new ExtendedPoint(0, 0);
	
	private SimplePath vehicle = new SimplePath();
	private SimplePath forks = new SimplePath();
	
	private boolean forkOuter = false;
	
	private SubjectShape load = null;

	public PathLifterShape() {
		super();
	}

	public PathLifterShape(double arg0, double arg1, int x_index, int y_index) {
		
		forks.setGroup(MainObject.FORK);
		forks.setName("fork");
		
		init(arg0, arg1, DEFAULT_WIDTH, DEFAULT_HEIGHT, x_index, y_index);
	}

	public PathLifterShape(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {
		
		forks.setGroup(MainObject.FORK);
		forks.setName("fork");

		init(arg0, arg1, arg2, arg3, x_index, y_index);
	}
	
	private void init(double x, double y, double w, double h, int x_index, int y_index) {
		
		this.x_vehicle = x;
		this.y_vehicle = y;
		this.x_fork = x + forkOffset.getX();
		this.y_fork = y + forkOffset.getY();
		
		this.x_index = x_index;
		this.y_index = y_index;
		
		this.width = w;
		this.height = h;
		
		buildforklifter();
		
//		System.out.println("forks.toString()"+forks.toString());
	}
	
	private void buildforklifter() {
		
		vehicle.reset();
		forks.reset();
		
		// Bewegliche Teile des Staplers: Fahrzeug
		vehicle.moveTo(x_vehicle, y_vehicle);
		vehicle.lineTo(x_vehicle+width, y_vehicle);
		vehicle.lineTo(x_vehicle+width, y_vehicle+height);
		vehicle.lineTo(x_vehicle, y_vehicle+height);
		vehicle.lineTo(x_vehicle, y_vehicle);
		vehicle.closePath();
		
		if(direction==MainObject.UP || direction==MainObject.DOWN) {
			
			// Ausfahrbare Teile des Staplers: Linke Gabel
			forks.moveTo(x_fork, y_fork);
			forks.lineTo(x_fork+0.333*width, y_fork);
			forks.lineTo(x_fork+0.333*width, y_fork+height);
			forks.lineTo(x_fork, y_fork+height);
			forks.lineTo(x_fork, y_fork);
	
			// Ausfahrbare Teile des Staplers: Rechte Gabel
			forks.moveTo(x_fork+width - 0.333*width, y_fork);
			forks.lineTo(x_fork+width, y_fork);
			forks.lineTo(x_fork+width, y_fork+height);
			forks.lineTo(x_fork+width - 0.333*width, y_fork+height);
			forks.lineTo(x_fork+width - 0.333*width, y_fork);
			forks.closePath();
		}
		else {
			
			// Ausfahrbare Teile des Staplers: Obere Gabel
			forks.moveTo(x_fork, y_fork);
			forks.lineTo(x_fork+width, y_fork);
			forks.lineTo(x_fork+width, y_fork+0.333*height);
			forks.lineTo(x_fork, y_fork+0.333*height);
			forks.lineTo(x_fork, y_fork);
	
			// Ausfahrbare Teile des Staplers: Untere Gabel
			forks.moveTo(x_fork, y_fork+0.666*height);
			forks.lineTo(x_fork+width, y_fork+0.666*height);
			forks.lineTo(x_fork+width, y_fork+height);
			forks.lineTo(x_fork, y_fork+height);
			forks.lineTo(x_fork, y_fork+0.666*height);
			forks.closePath();
		}
		
		vehicle.append(forks, true);		
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
		return vehicle.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return vehicle.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return vehicle.contains(arg0, arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return vehicle.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		return vehicle.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return vehicle.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		return vehicle.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		return vehicle.getPathIterator(arg0, arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		return vehicle.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		return vehicle.intersects(arg0, arg1, arg2, arg3);
	}

	@Override
	public double getCenterX() {
		return x_vehicle +  0.5*width; //vehicle.getBounds2D().getCenterX();
	}

	@Override
	public double getCenterY() {
		return y_vehicle + 0.5*height; //vehicle.getBounds2D().getCenterY();
	}

	@Override
	public double getX() {
		return x_vehicle; //vehicle.getBounds2D().getX();
	}

	@Override
	public double getY() {
		return y_vehicle; //vehicle.getBounds2D().getY();
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
	public void setRect(double x, double y, double width, double height) {
		
//		System.out.println("PathShape: setRect-> x="+x+" y="+y+" width="+width+" height"+height);

		this.x_vehicle = x;
		this.y_vehicle = y;
		this.x_fork = x;
		this.y_fork = y;

		buildforklifter();
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
		
		this.direction = direction;
		
		if(!forks.isShapeLocked()) // && !forkOuter)
		{
//			forkOuter = true;
			forks.lockShape();
			ForkMover forkMover = new ForkMover(forkOffset, this, forks, direction, component);
			forkMover.start();
			try { forkMover.join(); }
			catch (InterruptedException e) { e.printStackTrace(); }
			
			switch(direction) {
			
				case MainObject.UP:
					direction = MainObject.DOWN;
					break;
					
				case MainObject.DOWN:
					direction = MainObject.UP;
					break;
					
				case MainObject.LEFT:
					direction = MainObject.RIGHT;
					break;
					
				default:
					direction = MainObject.LEFT;
					break;				
			}
			
			forkMover = new ForkMover(forkOffset, this, forks, direction, component);
			forkMover.start();
			try { forkMover.join(); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		
	}

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

	@Override
	public void updateObject() {
		
//		System.out.println("PathLifterShape: updateObject()");
		
		init(x_vehicle, y_vehicle, width, height, x_index, y_index);
	}
	
	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public SimplePath getForks() {
		return forks;
	}

}
