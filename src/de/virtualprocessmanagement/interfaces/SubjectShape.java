package de.virtualprocessmanagement.interfaces;

import java.awt.Color;
import java.awt.Shape;

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

	public int getY_index();

	public String getName();

	public void setName(String name);
	
	public int getId();

	public void setId(int id);
	
	public int getMapId();
	
	public void setMapId(int mapId);
	
	public boolean isShowId();

	public void setShowId(boolean showId);
	
	public void lockShape();
	
	public void unlockShape();
	
	public boolean isShapeLocked();
	
	public double getX();
	
	public double getY();
	
	public String toString();
}
