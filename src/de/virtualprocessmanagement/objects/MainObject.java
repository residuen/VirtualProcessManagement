package de.virtualprocessmanagement.objects;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.processing.Mover;

public class MainObject {
	
	// nicht bewegliche shapes
	public static final int STORAGE_OBJECT = 0;
	public static final int MACHINE_WAY_OBJECT = 1;
	public static final int HUMAN_WAY_OBJECT = 2;

	// (teil)bewegliche Shapes
	public static final int PARTIAL_MOVEABLE_OBJECT = 3;
	public static final int MOVEABLE_OBJECT = 4;
	public static final int CHARGE_OBJECT = 5;
	public static final int FORKLIFT = 6;
	public static final int FORK = 7;
	public static final int ROBOT = 8;
	
	public static final int[] staticShapeKeysNumbers = new int[] { STORAGE_OBJECT, MACHINE_WAY_OBJECT, HUMAN_WAY_OBJECT };
	public static final int[] moveableShapeKeysNumbers = new int[]{ PARTIAL_MOVEABLE_OBJECT, MOVEABLE_OBJECT, FORKLIFT, ROBOT, CHARGE_OBJECT };

	public static final String[] staticShapeKeys = new String[] { Integer.toString(STORAGE_OBJECT),
																  Integer.toString(MACHINE_WAY_OBJECT),
																  Integer.toString(HUMAN_WAY_OBJECT) };
	
	public static final String[] moveableShapeKeys = new String[]{ Integer.toString(PARTIAL_MOVEABLE_OBJECT),
															  	   Integer.toString(MOVEABLE_OBJECT),
															  	   Integer.toString(FORKLIFT),
																   Integer.toString(ROBOT),
															  	   Integer.toString(CHARGE_OBJECT) };
	
	public static int NO_DIRECTION = 9;
	public static int X_DIRECTION = 10;
	public static int Y_DIRECTION = 11;
	public static int Z_DIRECTION = 12;
	
	public static final int LEFT = 13;
	public static final int UP = 14;
	public static final int RIGHT = 15;
	public static final int DOWN = 16;
	
	public static final double DEFAULT_WIDTH = 25, DEFAULT_HEIGHT = 25;
	
	protected int id = 0;
	
	protected int group = 0;
	
	protected String name = null;

	protected int direction = MainObject.UP;
	
	protected int x_index = 0, y_index = 0;
	
	protected int groupId = STORAGE_OBJECT;
	protected int directions = NO_DIRECTION;
	
	protected double width = DEFAULT_WIDTH;
	protected double height = DEFAULT_HEIGHT;
	
	protected double x = 0;
	protected double y = 0;
	
	protected boolean showId = true;
	
	protected boolean lock = false;

	protected Color fillColor = Color.RED, collisionsColor = Color.CYAN, frameColor = Color.BLACK;
	
	protected ArrayList<Mover> movers = new ArrayList<Mover>();

	public boolean hasLifterCharge() {
		return false;
	}
	
	public void chargeLoad(int direction, SubjectShape load, Component component) {
		
	}
	
	public void addMover(Mover mover) {
		
		movers.add(mover);
		
//		if(!movers.get(0).isMoverRunMode())
			runMovers();
		
	}
	
	private void runMovers() {

//		System.out.println("movers.get(0).isMoverRunMode()="+movers.get(0).isMoverRunMode());

		while(movers.size() > 0) {
			
//			System.out.println("movers.get(0).isMoverRunMode()="+movers.get(0).isMoverRunMode());
			
			if(!movers.get(0).isMoverRunMode())
			{
				System.out.println("movers.get(0).isMoverRunMode()="+movers.get(0).isMoverRunMode());

				movers.get(0).setMoverRunMode(true);
				movers.get(0).start();
				
				while(movers.get(0).isMoverRunMode())
				{
//					System.out.println("movers.get(0).isMoverRunMode()="+movers.get(0).isMoverRunMode());
				}
				
				movers.remove(0);
			}
		}
	}
}
