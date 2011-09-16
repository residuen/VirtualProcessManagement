/*
 * Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
 * wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
 * Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
 * Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
 * OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
 * EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
 * Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/

package de.virtualprocessmanagement.server;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.interfaces.Message;
import de.virtualprocessmanagement.processing.ProcessMap;
import de.virtualprocessmanagement.tools.ServerInfos;

/**
 * The Server ist based on the webserver coded by "Jon Berg"
 * You find ist at http://fragments.turtlemeat.com/javawebserver.php
 * I added some interfaces and extensions
 * Thanks to Jon for the basic-works
 * 
 * Copyright: 2011
 * @author: Karsten Bettray
 * @version 0.1
 * Licence: GPL 3.0 or higher
 * file: Server.java
*/

public class Server extends Thread implements HTTPServer {
	
    private ServerSocket serversocket = null;
    
//    private ServerClientConnectionLayer connectionLayer = null;
    
    private ProcessMap processMap = null;
    
    private Component component = null; // Das Panel in dem gezeichnet wird
    
    private ServerInfos serverInfos = new ServerInfos();
    
	private Message message_to; //the starter class, needed for gui
	private int port; //port we are going to listen to

	// Strings fuer die Anfrage-URI des Clients
    private String requestPath = "";
    private String requestText = "";

	//the constructor-parameters it takes is what port to bind to, the default tcp port
    //for a httpserver is port 80. the other parameter is a reference to
    //the gui, this is to pass messages to our nice interface
	public Server(int listen_port, Message to_send_message_to, ProcessMap processMap, Component component) {
	  
		message_to = to_send_message_to;
		port = 80; // listen_port;
		this.processMap= processMap; 
		this.component = component;
		
		this.setPriority(6);

		this.start();
	}

	/**
	 * Sendet eine Nachricht an die ServerGui
	 * @param msg
	 */
	private void serverMessage(String msg) { //an alias to avoid typing so much!
		message_to.message(msg);
	}

//this is a overridden method from the Thread class we extended from
	public void run() {
	  
		// we are now inside our own thread separated from the gui.
		// ServerSocket serversocket = null;
		// To easily pick up lots of girls, change this to your name!!!
		serverMessage("<The VirtualProzessManagement-Server>\n");
		serverMessage("<Type http://"+serverInfos.getServerIP()+"/client?getserverinfo in browser to test>\n\n");
    
		//Pay attention, this is where things starts to cook!
		try {
			//print/send message to the guiwindow
			serverMessage("Trying to bind to localhost on port " + Integer.toString(port) + "...");
      
			//make a ServerSocket and bind it to given port,
			serversocket = new ServerSocket(port);
		}
		catch (Exception e) { //catch any errors and print errors to gui
			serverMessage("\nFatal Error:" + e.getMessage());
			return;
		}
		
		serverMessage("OK!\n");
		
		//go in a infinite loop, wait for connections, process request, send response
		while (!isInterrupted() && !serversocket.isClosed()) {
			
			serverMessage("\nReady, Waiting for requests...\n");
			try {
				//this call waits/blocks until someone connects to the port we are listening to
//				serverMessage("connectionsocket");
				Socket connectionsocket = serversocket.accept();
//		     	serverMessage("connectionsocket");
     	        
				//figure out what ipaddress the client commes from, just for show!
				InetAddress client = connectionsocket.getInetAddress();
				//and print it to gui
				serverMessage(client.getHostName() + " connected to server.\n");
        
				//Read the http request from the client from the socket interface
				//into a buffer.
				BufferedReader input = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
        
				//Prepare a outputstream from us to the client,
				//this will be used sending back our response
				//(header + requested file) to the client.
				OutputStreamWriter output = new OutputStreamWriter(connectionsocket.getOutputStream());

				//as the name suggest this method handles the http request, see further down.
				//abstraction rules
				http_handler(input, output);
			}
			catch (Exception e) { //catch any errors, and print them
				serverMessage("\n1: Error:" + e.getMessage());
			}

		} //go back in loop, wait for next request
	}

	private void http_handler(BufferedReader input, OutputStreamWriter output) {
	  
		int method = 0; //1 get, 2 head, 0 not supported

		String path = new String(); //the various things, what http v, what path,
	  
		try {
			String tmp = null;
			
			try {
				tmp = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			} //read from the stream
			
//			System.out.println("tmp="+tmp);
			String tmp2 = new String(tmp);
			tmp.toUpperCase(); //convert it to uppercase
			if (tmp.startsWith("GET")) { //compare it is it GET
				method = 1;
			} //if we set it to method 1
			if (tmp.startsWith("HEAD")) { //same here is it HEAD
				method = 2;
			} //set method to 2

			if (method == 0) { // not supported
				try {
					output.write(construct_http_header(501, 0));
					output.close();
					return;
				}
				catch (Exception e3) { //if some error happened catch it
					serverMessage("2: error:" + e3.getMessage());
				} //and display error
			}
	
	      //tmp contains "GET /index.html HTTP/1.0 ......."
	      //find first space
	      //find next space
	      //copy whats between minus slash, then you get "index.html"
	      //it's a bit of dirty code, but bear with me...
			int start = 0;
			int end = 0;

			for (int a = 0; a < tmp2.length(); a++) {
		    	  
				if (tmp2.charAt(a) == ' ' && start != 0) {
					end = a;
					break;
				}
		        
				if (tmp2.charAt(a) == ' ' && start == 0) {
					start = a;
				}
			}
			path = tmp2.substring(start + 2, end); //fill in the path
		}
		catch (Exception e) {
			serverMessage("3: error " + e.getMessage());
			serverMessage(e.getLocalizedMessage());
		} //catch any exception

		requestText = new File(path).getName();
//		requestPath = new File(path).getAbsolutePath().replace(requestText, "");
    
		// path do now have the filename to what to the file it wants to open
//		serverMessage("\nClient requested: requestPath=" + requestPath);
		serverMessage("\nClient requested: requestText=" + requestText + "\n");
    
		//happy day scenario
		
		ServerClientConnectionLayer connectionLayer = new ServerClientConnectionLayer(processMap);
		connectionLayer.clientRequest(requestText, this, output, component);
//		sendResponseText(requestText, output);	// Periodisches Senden an den Client

	}
  
  //this method makes the HTTP header for the response
  //the headers job is to tell the browser the result of the request
  //among if it was successful or not.
	private String construct_http_header(int return_code, int file_type) {
		
		String s = "HTTP/1.0 ";
    //you probably have seen these if you have been surfing the web a while
		switch (return_code) {
			case 200:
				s = s + "200 OK";
				break;
			case 400:
				s = s + "400 Bad Request";
				break;
			case 403:
				s = s + "403 Forbidden";
				break;
			case 404:
			  s = s + "404 Not Found";
			  break;
			case 500:
				s = s + "500 Internal Server Error";
				break;
			case 501:
				s = s + "501 Not Implemented";
				break;
		}

		s = s + "\r\n"; //other header fields,
		s = s + "Connection: close\r\n"; //we can't handle persistent connections
		s = s + "Server: SimpleHTTPtutorial v0\r\n"; //server name
		s = s + "Content-Type: text/html\r\n";
		s = s + "\r\n"; //this marks the end of the httpheader
		// and the start of the body
		// ok return our newly created header!
    
		return s;
	}
  	
	/**
	* Sending the data to the client
	*/
	@Override
	public void sendResponseText(final String[] text, final OutputStreamWriter output) {
	  
		//happy day scenario
		try {
    	
			// Die Server-Antwort an den Client 
//			output.write(construct_http_header(200, 5));
//			output.writeBytes(construct_http_header(200, 5));
			
			for(String s : text)
			{
				output.write(s+"\n");

				// Infotext fuer WebserverGui
				serverMessage("message to client:"+s);
				
//				System.out.println("Text="+s);
			}

	        //clean up the files, close open handles
	    	output.close();
	    }

	    catch (Exception e) {}
  }

	/**
	* Return of the Jedis ... sorry, the ServerSocket
	*/
	@Override
	public ServerSocket getServersocket() {
		return serversocket;
	}
	
	/**
	 * Der Pfad der HTTP-Anfrage
	 * @return
	 */
	@Override
	public String getRequestPath() {
		return requestPath;
	}

	/**
	 * Name der angefragten Datei
	 * @return
	 */
	@Override
	public String getRequestText() {
		return requestText;
	}

	public void setConnectionLayer( ServerClientConnectionLayer connectionLayer) {
//		this.connectionLayer = connectionLayer;
		
		connectionLayer.setServer(this);
	}

}