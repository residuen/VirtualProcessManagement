package de.virtualprocessmanagement.interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Shape;

import de.virtualprocessmanagement.processing.Mover;

/**
 * "Overall"-Interface fuer alle grafischen Objekte
 * Beinhaltet alle Eigenschaften der Shapes,
 * sowie die Bewegungsmethoden
 * @author bettray
 *
 */
public interface SubjectShape extends Shape {
	
	public Color getFillColor();
	public Color getCollisionsColor() ;
	
	public Color getFrameColor() ;
	
	public int getGroup();
	
	public String getGroupAsString();
	
	public void setGroup(int subjectTyp);
	
	public int getDirections();

	public void setDirections(int directions);

	public double getWidth();

	public void setWidth(double width);

	public double getHeight();

	public void setHeight(double height);
	
	public double getCenterX();
	
	public double getCenterY();
	
	public int getX_index();

	public void setX_index(int x_index);

	public int getY_index();

	public void setY_index( int y_index);

	public String getName();

	public void setName(String name);
	
	public int getId();

	public void setId(int id);
	
	public int getMapId();
	
	public void setMapId(int mapId);
	
	public boolean isShowId();

	public void setShowId(boolean showId);
	
	// Verriegelungs-Methoden um Shape vor Manipulation zu schuetzen
	public void lockShape();
	public void unlockShape();
	public boolean isShapeLocked();
	
	public double getX();
	
	public double getY();
	
	public String toString();
	
	public void setRect(double x, double y, double width, double height);
	
	public boolean hasMoveableAdditionalShape();
	
	public SubjectShape getAdditionalShape();
	
	public void setAdditionalShape(SubjectShape shape);
	
	public boolean hasLifterCharge();	// Stapler beladen?
	
	public void chargeLoad(int direction, SubjectShape load, Component component);	// Objekt beladen
	
	public void dischargeLoad(int direction, Component component);	// Objekt entladen
	
//	public void chargeLoad();
	
	public void updateObject();
	
	public int getDirection();

	public void setDirection(int direction);
	
	public void addMover(Mover mover);
}
