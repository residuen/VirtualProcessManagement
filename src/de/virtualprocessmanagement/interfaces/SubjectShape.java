package de.virtualprocessmanagement.interfaces;

import java.awt.Color;
import java.awt.Shape;

public interface SubjectShape extends Shape {
	
	public Color getFillColor();
	public Color getCollisionsColor() ;
	
	public Color getFrameColor() ;
	
	public int getSubjectTyp();
	
	public String getSubjectTypAsString();
	
	public void setSubjectTyp(int subjectTyp);
	
	public int getDirections();

	public void setDirections(int directions);

	public double getWidth();

	public void setWidth(double width);

	public double getHeight();

	public void setHeight(double height);
	
	public int getX_index();

	public int getY_index();

	public String getName();

	public void setName(String name);
	
	public int getId();

	public void setId(int id);
	
	public boolean isShowId();

	public void setShowId(boolean showId);
}
