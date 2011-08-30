package de.virtualprocessmanagement.interfaces;

public interface ShapeHandler {
	
	public void moveObject(int objectGroup, int objectMapId, String direction);
	public void moveObject(int objectId, String direction);
	
	public void chargeObjectByGroup(int objectGroup, int objectMapId, String direction);
	public void chargeObjectById(int objectIdLifter, int objectIdCharge, String direction);
	
	public void dischargeObjectById(int lifterObjectId, String direction);

}
