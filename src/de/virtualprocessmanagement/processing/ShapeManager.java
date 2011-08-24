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
	
	@Override
	public void moveObject(int objectGroup, int objectMapId, String direction) {
		
		direction = direction.toLowerCase();
		
		SubjectShape shape = processMap.getObjectList(objectGroup).get(objectMapId);
	
		objectMover(shape, direction);
	}
	
	@Override
	public void moveObject(int objectId, String direction) {
		
		direction = direction.toLowerCase();
		
		SubjectShape shape = processMap.getAllObjects().get(objectId);
	
		objectMover(shape, direction);
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
	public void objectMover(SubjectShape shape, String direction) {
		
		direction = direction.toLowerCase();
		
//		System.out.println("ShapeManager: Move Object: "+objectGroup+" "+objectId+" "+direction);
		
		ShapeMover mover = null;
		
		if(!shape.isShapeLocked())
		{
			if(direction.equals("up"))
				mover = new ShapeMover(shape, RectShape.UP, 1000, visuComponent);
	
			else if(direction.equals("down"))
				mover = new ShapeMover(shape, RectShape.DOWN, 1000, visuComponent);
	
			else if(direction.equals("left"))
				mover = new ShapeMover(shape, RectShape.LEFT, 1000, visuComponent);
	
			else if(direction.equals("right"))
				mover = new ShapeMover(shape, RectShape.RIGHT, 1000, visuComponent);
			
			shape.lockShape();
		
			if(mover!=null)
			{
				mover.start();
				try { mover.join(); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		System.out.println("Shape to move:"+shape);
	}
	
	public void setVisuComponent(Component visuComponent) {
		this.visuComponent = visuComponent;
	}
}
