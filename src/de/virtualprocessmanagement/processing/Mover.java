package de.virtualprocessmanagement.processing;

public class Mover extends Thread {
	
	protected boolean moverRunMode = false;
	
	public boolean isMoverRunMode() {
		return moverRunMode;
	}
	
	public void  setMoverRunMode(boolean b) {
		this.moverRunMode = b;
	}

}
