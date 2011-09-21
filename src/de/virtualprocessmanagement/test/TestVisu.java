package de.virtualprocessmanagement.test;

import de.virtualprocessmanagement.connection.HTTPClientConnection;

public class TestVisu extends Thread {
	
	private boolean runMode = true;
	
	private int sleepTime = 1000;
	
	private HTTPClientConnection connection = new HTTPClientConnection(); //"127.0.0.1");
		
    public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}

	public void run() {
    	
    	while(!isInterrupted() && runMode)
        {
    		System.out.println("TestVisu");
    		
    		try {
    			connection.sendRequest("?client=foo&cmd=info");
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
        }
    }
}
