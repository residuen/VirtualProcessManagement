package de.virtualprocessmanagement.visu;

import de.virtualprocessmanagement.connection.HTTPClientConnection;
import de.virtualprocessmanagement.interfaces.Message;

/**
 * Beipiel-Client, empfaengt Daten VOM Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class ReadServerData extends Thread {

	HTTPClientConnection connection = new HTTPClientConnection("localhost");

	Message to_send_message_to = null;
	
	boolean runMode = true;

	String data = null;
	
	String command = "http://localhost:80/visu?loadobject";
	
	int sleepTime = 1000;
	
	public ReadServerData() { }
	
	public ReadServerData(Message to_send_message_to) {
		
		this.to_send_message_to = to_send_message_to;
	}
	
	public void run() {
    	
    	while(!isInterrupted() && runMode)
        {    		
    		data = connection.sendRequest(command);
    		
    		System.out.println("ReadServerData: data="+data);
    		
    		to_send_message_to.message(data+"\n");
    		
    		try {
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    			System.out.println(e.getMessage());
    		}
        }
    }
	
	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}
    
    protected void setCommand(String command) {
		synchronized (command) {
			this.command = command;
		}
	}

	protected void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public HTTPClientConnection getConnection() {
		return connection;
	}

	public static void main(String[] arg0)
	{
		new ReadServerData().start();
	}

}
