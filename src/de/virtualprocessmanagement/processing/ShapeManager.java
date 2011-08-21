package de.virtualprocessmanagement.processing;

import java.awt.Component;

import de.virtualprocessmanagement.interfaces.ShapeHandler;
import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.RectShape;

public class ShapeManager implements ShapeHandler {

	private ProcessMap processMap = null;
	private Component visuComponent = null;
	
	public ShapeManager(ProcessMap processMap) {
		this.processMap = processMap;
	}
	
	/**
	 * Move object to a special cell in field
	 * request: client?moveObject=objectGroup,objectId,left/up/right/down
	 * example: client?moveobject=2,0,up
	 * @param objectGroup
	 * @param objectId
	 * @param x
	 * @param y
	 */
	@Override
	public void moveObject(int objectGroup, int objectId, String direction) {
		
		direction = direction.toLowerCase();
		
		System.out.println("ShapeManager: Move Object: "+objectGroup+" "+objectId+" "+direction);
		
		SubjectShape shape = processMap.getObjectList(objectGroup).get(objectId);
		
		if(direction.equals("up"))
			((RectShape)shape).setRect(shape.getBounds2D().getX(),
									   shape.getBounds2D().getY() - RectShape.DEFAULT_HEIGHT,
									   shape.getWidth(),
									   shape.getHeight());
		
		else if(direction.equals("down"))
			((RectShape)shape).setRect(shape.getBounds2D().getX(),
					   shape.getBounds2D().getY() + RectShape.DEFAULT_HEIGHT,
					   shape.getWidth(),
					   shape.getHeight());
		
		else if(direction.equals("left"))
			((RectShape)shape).setRect(shape.getBounds2D().getX() - RectShape.DEFAULT_WIDTH,
					   shape.getBounds2D().getY(),
					   shape.getWidth(),
					   shape.getHeight());
		
		else if(direction.equals("right"))
			((RectShape)shape).setRect(shape.getBounds2D().getX() + RectShape.DEFAULT_WIDTH,
					   shape.getBounds2D().getY(),
					   shape.getWidth(),
					   shape.getHeight());
		
		visuComponent.repaint();
		
		System.out.println("Shape to move:"+shape);
	}
	
	public void setVisuComponent(Component visuComponent) {
		this.visuComponent = visuComponent;
	}

}
