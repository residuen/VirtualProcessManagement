package de.virtualprocessmanagement.processing;

import java.awt.Component;
import java.awt.geom.GeneralPath;
import java.util.Vector;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.*;

/**
 * Verschiebt verriegelte Shapes in vorgegebene Zellen und entsperrt sie anschliessend
 * @author bettray
 *
 */
public class ForkMover extends Mover {
	
	private SubjectShape mainShape = null;
	
	private Vector<SubjectShape> shapeList = new Vector<SubjectShape>();
	
	private int sleepTime = 100;
	
	private int direction = MainObject.UP;
	
	private Component component = null;
	
	private double x = 0, x_new = 0, y = 0, y_new = 0;
	
	private ExtendedPoint forkOffset = null;
	
	public ForkMover(ExtendedPoint forkOffset, SubjectShape mainShape, SubjectShape shape, int direction, Component component) {
		
		this.forkOffset = forkOffset;
		this.mainShape = mainShape;
		this.direction = direction;
		this.component = component;

		this.shapeList.add(shape);
	}
	
	public ForkMover(ExtendedPoint forkOffset, SubjectShape mainShape, SubjectShape shape, SubjectShape load, int direction, Component component) {
		
		this.forkOffset = forkOffset;
		this.mainShape = mainShape;
		this.direction = direction;
		this.component = component;

		this.shapeList.add(shape);
		this.shapeList.add(load);
	}

	public void run() {
		
		if(direction==RectShape.LEFT)
		{
			x = -RectShape.DEFAULT_WIDTH / (double)sleepTime; // Verschiebung in x-Richtung
			
			for(SubjectShape shape : shapeList)
			{
				x_new = shape.getBounds2D().getX() - RectShape.DEFAULT_WIDTH; // Endwert x
				y_new = shape.getBounds2D().getY();	// Endwert y
			
				shape.setX_index(shape.getX_index() - 1);
			}
		}
		else
		if(direction==RectShape.RIGHT)
		{
			x = RectShape.DEFAULT_WIDTH / (double)sleepTime;	// s.o.
			
			for(SubjectShape shape : shapeList)
			{
				x_new = shape.getBounds2D().getX() + RectShape.DEFAULT_WIDTH;// s.o.
				y_new = shape.getBounds2D().getY();// s.o.
	
				shape.setX_index(shape.getX_index() + 1);
			}
		}
		else
		if(direction==RectShape.UP)
		{
			y = -RectShape.DEFAULT_HEIGHT / (double)sleepTime;	// vergl. oben
			for(SubjectShape shape : shapeList)
			{
				y_new = shape.getBounds2D().getY() - RectShape.DEFAULT_HEIGHT;	// vergl. oben
				x_new = shape.getBounds2D().getX();	// vergl. oben
	
				shape.setY_index(shape.getY_index() - 1);
			}
		}
		else
		if(direction==RectShape.DOWN)
		{
			y = RectShape.DEFAULT_HEIGHT / (double)sleepTime;	// vergl. oben
			
			for(SubjectShape shape : shapeList)
			{
				y_new = shape.getBounds2D().getY() + RectShape.DEFAULT_HEIGHT;	// vergl. oben
				x_new = shape.getBounds2D().getX();	// vergl. oben
	
				shape.setY_index(shape.getY_index() + 1);
			}
		}
		
		while(!isInterrupted() && testRectValues(shapeList.get(shapeList.size()-1)))
        {
			forkOffset.setLocation(forkOffset.getX() + x, forkOffset.getY() + y);
			
			// Zeichnen er neuen x-y-Koordinaten
			for(SubjectShape shape : shapeList)
			{
				shape.setRect(shape.getBounds2D().getX() + x,shape.getBounds2D().getY() + y, shape.getWidth(), shape.getHeight());			
			
				mainShape.updateObject();
				
				component.repaint();
	
	    		try {
	    			Thread.sleep(sleepTime / 10);
	    		} catch (InterruptedException e) {
	    			shape.unlockShape();
	    			System.out.println(e.getMessage());
	    		}
			}
		}
		
		finalizeShape();
    }
	
	private void finalizeShape() {
		
		for(SubjectShape shape : shapeList)
		{
			shape.setRect( x_new, y_new, shape.getWidth(), shape.getHeight());
	
			shape.unlockShape();
		}
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
