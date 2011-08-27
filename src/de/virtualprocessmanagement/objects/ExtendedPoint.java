package de.virtualprocessmanagement.objects;

import java.awt.geom.Point2D;

public class ExtendedPoint extends Point2D {

	private double x = 0, y = 0;
	
	public ExtendedPoint() {
		
		x = 0;
		y = 0;
	}
	
	public ExtendedPoint(double arg0, double arg1) {
		
		this.x = arg0;
		this.y = arg1;
	}

	@Override
	public double getX() {
		
		return x;
	}

	@Override
	public double getY() {

		return y;
	}

	@Override
	public void setLocation(double arg0, double arg1) {
		
		this.x = arg0;
		this.y = arg1;
	}

}
