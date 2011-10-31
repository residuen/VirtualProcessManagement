package de.virtualprocessmanagement.visu;

import de.virtualprocessmanagement.connection.HTTPClientConnection;
import de.virtualprocessmanagement.interfaces.Message;

/**
 * Beipiel-Client, empfaengt Daten vom Server und sendet Daten ZUM Server
 * @author bettray
 *
 */
public class ReadServerData extends Thread {

	HTTPClientConnection connection = null;

	Message to_send_message_to = null;
	
	boolean runMode = true;

	String data = null;
	
	String command = null;
	
	int sleepTime = 1000;
	
	public ReadServerData() { }
	
	public ReadServerData(Message to_send_message_to, String host) {
		
		this.to_send_message_to = to_send_message_to;
		
		command = "";
		
		connection = new HTTPClientConnection(); //host);
	}
	
	public void run() {
    	
    	while(!isInterrupted() && runMode)
        {   
    		if(command.length() > 0)
    		{
    			data = connection.sendRequest(command);
    		
	    		to_send_message_to.message(data+"\n");
	    		
	    		try {
	    			Thread.sleep(sleepTime);
	    		} catch (InterruptedException e) {
	    			System.out.println(e.getMessage());
	    		}
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

//	public static void main(String[] arg0)
//	{
//		new ReadServerData().start();
//	}
}
