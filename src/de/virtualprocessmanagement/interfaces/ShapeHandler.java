package de.virtualprocessmanagement.interfaces;

public interface ShapeHandler {
	
	public void moveObject(int objectGroup, int objectMapId, String direction);
	public void moveObject(int objectId, String direction);
	
	public void chargeObject(int objectGroup, int objectMapId, String direction);
	public void chargeObject(int objectId, String direction);

}
