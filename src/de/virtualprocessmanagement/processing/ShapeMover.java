package de.virtualprocessmanagement.processing;

import java.awt.Component;
import java.awt.geom.GeneralPath;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.*;

/**
 * Verschiebt verriegelte Shapes in vorgegebene Zellen und entsperrt sie anschliessend
 * @author bettray
 *
 */
public class ShapeMover extends Mover {
	
	private SubjectShape shape = null;
	
	private int sleepTime = 100;
	
	private int direction = RectShape.UP;
	
	private Component component = null;
	
	private double x = 0, x_new = 0, y = 0, y_new = 0;
	
	public ShapeMover(SubjectShape shape, int direction, int sleepTime, Component component) {
		
		this.shape = shape;
		this.direction = direction;
//		this.sleepTime = sleepTime / 10;
		this.component = component;
	}
	
	public void run() {	
		
		setMoverRunMode(true);

		if(direction==RectShape.LEFT)
		{
			x = -RectShape.DEFAULT_WIDTH / (double)sleepTime; // Verschiebung in x-Richtung
			x_new = shape.getBounds2D().getX() - RectShape.DEFAULT_WIDTH; // Endwert x
			y_new = shape.getBounds2D().getY();	// Endwert y
			
			shape.setX_index(shape.getX_index() - 1);
		}
		else
			if(direction==RectShape.RIGHT)
			{
				x = RectShape.DEFAULT_WIDTH / (double)sleepTime;	// s.o.
				x_new = shape.getBounds2D().getX() + RectShape.DEFAULT_WIDTH;// s.o.
				y_new = shape.getBounds2D().getY();// s.o.

				shape.setX_index(shape.getX_index() + 1);
			}
			else
				if(direction==RectShape.UP)
				{
					y = -RectShape.DEFAULT_HEIGHT / (double)sleepTime;	// vergl. oben
					y_new = shape.getBounds2D().getY() - RectShape.DEFAULT_HEIGHT;	// vergl. oben
					x_new = shape.getBounds2D().getX();	// vergl. oben

					shape.setY_index(shape.getY_index() - 1);
				}
				else
					if(direction==RectShape.DOWN)
					{
						y = RectShape.DEFAULT_HEIGHT / (double)sleepTime;	// vergl. oben
						y_new = shape.getBounds2D().getY() + RectShape.DEFAULT_HEIGHT;	// vergl. oben
						x_new = shape.getBounds2D().getX();	// vergl. oben

						shape.setY_index(shape.getY_index() + 1);
					}
		
		while(!isInterrupted() && testRectValues(shape))
        {
//			System.out.println("name="+shape.getName()+" x="+x+" x_neu="+x_new+" y="+y+" y_neu="+y_new+" shape->x="+(shape).getX()+" shape->y="+(shape).getY());
    		
//			System.out.println("shape.getBounds2D().getX() + x="+(shape.getBounds2D().getX()+" + " + x));
			
			// Zeichnen er neuen x-y-Koordinaten
			shape.setRect(shape.getBounds2D().getX() + x,shape.getBounds2D().getY() + y, shape.getWidth(), shape.getHeight());			
		
			component.repaint();

    		try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    			shape.unlockShape();
    			System.out.println(e.getMessage());
    		}
        }
		
		finalizeShape();
		
		setMoverRunMode(false);
    }
	
	private void finalizeShape() {
		
		shape.setRect( x_new, y_new, shape.getWidth(), shape.getHeight());

		shape.unlockShape();

	}
	
	private boolean testRectValues(SubjectShape shape) {
		
		if(direction==RectShape.UP)
			return shape.getY() >= y_new;
		else
			if(direction==RectShape.DOWN)
				return shape.getY() <= y_new;
			else
				if(direction==RectShape.LEFT)
					return shape.getX() >= x_new;
				else
					if(direction==RectShape.RIGHT)
						return shape.getX() <= x_new;

		return true;
	}
}