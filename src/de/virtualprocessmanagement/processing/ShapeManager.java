package de.virtualprocessmanagement.processing;

import java.awt.Component;

import de.virtualprocessmanagement.interfaces.ShapeHandler;
import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.MainObject;
import de.virtualprocessmanagement.objects.PathLifterShape;
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
	 * MUSS NOCH ERWEIERT WERDEN
	 */
	@Override
	public void chargeObjectByGroup(int objectGroup, int objectMapId, String direction) {

//		SubjectShape shape = processMap.getObjectList(objectGroup).get(objectMapId);
		
//		objectCharger(shape, direction);
	}

	@Override
	public void chargeObjectById(int objectIdLifter, int objectIdCharge, String direction) {

		SubjectShape lifterShape =  processMap.getAllObjects().get(objectIdLifter);
		SubjectShape chargeShape =  processMap.getAllObjects().get(objectIdCharge);

		objectCharger(lifterShape, chargeShape, direction);
	}
	
	@Override
	public void dischargeObjectById(int lifterObjectId, String direction) {
		
		SubjectShape lifterShape =  processMap.getAllObjects().get(lifterObjectId);

		objectDischarger(lifterShape, direction);
	}
	
	private void objectDischarger(SubjectShape shape, String direction) {
	
		direction = direction.toLowerCase();
		
		int dirAsInt = -1;
		
			if(direction.equals("up"))
				dirAsInt = RectShape.UP;
			else if(direction.equals("down"))
				dirAsInt = RectShape.DOWN;
			else if(direction.equals("left"))
				dirAsInt = RectShape.LEFT;
			else if(direction.equals("right"))
				dirAsInt = RectShape.RIGHT;
//	
//		if(shape.getGroup() == MainObject.FORKLIFT && dirAsInt >= 0)
			((PathLifterShape)shape).dischargeLoad(dirAsInt, visuComponent);
	}

	private void objectCharger(SubjectShape shape, SubjectShape charge, String direction) {
		
		direction = direction.toLowerCase();
		
		int dirAsInt = -1;
		
			if(direction.equals("up"))
				dirAsInt = RectShape.UP;
			else if(direction.equals("down"))
				dirAsInt = RectShape.DOWN;
			else if(direction.equals("left"))
				dirAsInt = RectShape.LEFT;
			else if(direction.equals("right"))
				dirAsInt = RectShape.RIGHT;
	
		if(shape.getGroup() == MainObject.FORKLIFT && dirAsInt >= 0)
			((PathLifterShape)shape).chargeLoad(dirAsInt, charge, visuComponent);
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
		
		int dirAsInt = 0;
		
		if(!shape.isShapeLocked())
		{
			if(direction.equals("up"))
				dirAsInt = RectShape.UP;
			else if(direction.equals("down"))
				dirAsInt = RectShape.DOWN;
			else if(direction.equals("left"))
				dirAsInt = RectShape.LEFT;
			else if(direction.equals("right"))
				dirAsInt = RectShape.RIGHT;
			
			mover = new ShapeMover(shape, dirAsInt, 1000, visuComponent);
			
			shape.lockShape();
		
			if(mover!=null)
			{
				mover.start();
				try { mover.join(); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		
//		if(shape.getY_index() == 3 && shape instanceof PathLifterShape)
//		{
//			((PathLifterShape)shape).getForks().chargeLoad(dirAsInt, 1000, visuComponent);
//		}

		
		System.out.println("Shape to move:"+shape);
	}
	
	public void setVisuComponent(Component visuComponent) {
		this.visuComponent = visuComponent;
	}

}
