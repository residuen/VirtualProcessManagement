/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package de.virtualprocessmanagement.connection.apache;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Locale;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.util.EntityUtils;

import de.virtualprocessmanagement.connection.ServerClientConnectionLayer;
import de.virtualprocessmanagement.interfaces.HTTPServer;
import de.virtualprocessmanagement.interfaces.Message;

/**
 * Basic, yet fully functional and spec compliant, HTTP/1.1 file server.
 * <p>
 * Please note the purpose of this application is demonstrate the usage of HttpCore APIs.
 * It is NOT intended to demonstrate the most efficient way of building an HTTP file server. 
 * 
 *
 */
public class ElementalHttpServer implements HTTPServer {

    private ServerSocket serversocket = null;
    
    private ServerClientConnectionLayer connectionLayer = null;
    
//    private ServerInfos serverInfos = new ServerInfos();
    
	private Message message_to; //the starter class, needed for gui
	private int port; //port we are going to listen to

	// Strings fuer die Anfrage-URI des Clients
    private String requestPath = "";
    private String requestText = "";

	public ElementalHttpServer(int listen_port, Message to_send_message_to) {
		  
		message_to = to_send_message_to;
		port = 80; // listen_port;

        Thread t = null;
		try {
			t = new RequestListenerThread(80, new File("").toString(), this);
	        t.setDaemon(false);
	        t.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class HttpFileHandler implements HttpRequestHandler  {
        
        private final String docRoot;
        private final HTTPServer httpServer;
        
        public HttpFileHandler(final String docRoot, final HTTPServer httpServer) {
        	
            super();
            
            this.docRoot = docRoot;
            this.httpServer = httpServer;
        }
        
        public void handle(        		
                final HttpRequest request, 
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {

            String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                throw new MethodNotSupportedException(method + " method not supported"); 
            }
            
            final String target = request.getRequestLine().getUri();
            
            System.out.println("target="+target);
            
    		// path do now have the filename to what to the file it wants to open
//    		serverMessage("\nClient requested: requestPath=" + requestPath);
    		serverMessage("\nClient requested: requestText=" + target + "\n");
        
    		//happy day scenario
 
//    		sendResponseText(requestText, writer);	// Periodisches Senden an den Client


            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                byte[] entityContent = EntityUtils.toByteArray(entity);
                System.out.println("Incoming entity content (bytes): " + entityContent.length);
            }
            
            final File file = new File(this.docRoot, URLDecoder.decode(target));
//            if (!file.exists())
            {

                response.setStatusCode(HttpStatus.SC_NOT_FOUND);
                EntityTemplate body = new EntityTemplate(new ContentProducer() {
                    
                    public void writeTo(final OutputStream outstream) throws IOException {
                        OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8"); 
//                        writer.write("<html><body><h1>");
//                        writer.write("File ");
//                        writer.write(file.getPath());
//                        writer.write(" not found");
//                        writer.write(target);
//                        writer.write("</h1></body></html>");
                        
//                        writer.write(target);
//                        writer.flush();
                        
//                        System.out.println("ApacheServer-> "+file.getPath());
//                		sendResponseText(requestText, writer);	// Periodisches Senden an den Client
                        System.out.println("Dude, did u find the connectionLayer??");
                        serverMessage("\nDude, did u find the connectionLayer??");

                   		connectionLayer.clientRequest(file.getName(), httpServer, writer);
//                        writer.flush();

                    }
                    
                });
//                body.setContentType("text/html; charset=UTF-8");
                response.setEntity(body);
//                System.out.println("File " + file.getPath() + " not found");
                
            }
        }
    }
    
    class RequestListenerThread extends Thread {

        private final ServerSocket serversocket;
        private final HttpParams params; 
        private final HttpService httpService;
        private final HTTPServer httpServer;
        
        public RequestListenerThread(int port, final String docroot, final HTTPServer httpServer) throws IOException {
            this.serversocket = new ServerSocket(port);
            this.params = new SyncBasicHttpParams();
            this.params
                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
                .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
                .setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1");
            this.httpServer = httpServer;

            // Set up the HTTP protocol processor
            HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpResponseInterceptor[] {
                    new ResponseDate(),
                    new ResponseServer(),
                    new ResponseContent(),
                    new ResponseConnControl()
            });
            
            // Set up request handlers
            HttpRequestHandlerRegistry reqistry = new HttpRequestHandlerRegistry();
            reqistry.register("*", new HttpFileHandler(docroot, httpServer));
            
            // Set up the HTTP service
            this.httpService = new HttpService(
                    httpproc, 
                    new DefaultConnectionReuseStrategy(), 
                    new DefaultHttpResponseFactory(),
                    reqistry,
                    this.params);
        }
        
        public void run() {
        	
        	System.out.println("Listening on port " + this.serversocket.getLocalPort());
            
        	while (!Thread.interrupted()) {
        		
        		try {
					// Set up HTTP connection
					Socket socket = this.serversocket.accept();
					DefaultHttpServerConnection conn = new DefaultHttpServerConnection();
					System.out.println("Incoming connection from " + socket.getInetAddress());
					conn.bind(socket, this.params);

					// Start worker thread
					Thread t = new WorkerThread(this.httpService, conn);
					t.setDaemon(true);
					t.start();
                    
        		} catch (InterruptedIOException ex) {
        			break;
        		} catch (IOException e) {
        			System.err.println("I/O error initialising connection thread: " + e.getMessage());
        			break;
        		}
        	}
        }
	}
    
    class WorkerThread extends Thread {

		private final HttpService httpservice;
		private final HttpServerConnection conn;

		public WorkerThread(final HttpService httpservice, final HttpServerConnection conn) {
			
					super();
					this.httpservice = httpservice;
					this.conn = conn;
        }

		public void run() {
			System.out.println("New connection thread");
			HttpContext context = new BasicHttpContext(null);
			
			try {
				while (!Thread.interrupted() && this.conn.isOpen()) {
					this.httpservice.handleRequest(this.conn, context);
				}
			} catch (ConnectionClosedException ex) {
				System.err.println("Client closed connection");
			} catch (IOException ex) {
				System.err.println("I/O error: " + ex.getMessage());
			} catch (HttpException ex) {
				System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());
			} finally {
				try {
					this.conn.shutdown();
				} catch (IOException ignore) {}
			}
		}
	}

	/**
	* Return of the Jedis ... sorry, the ServerSocket
	*/
	@Override
	public ServerSocket getServersocket() {
		return serversocket;
	}

	public void setConnectionLayer(ServerClientConnectionLayer connectionLayer) {
		this.connectionLayer = connectionLayer;
		
		System.out.println("ElementalHttpServer:setSimulationController");
		
		connectionLayer.setServer(this);
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

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Sendet eine Nachricht an die ServerGui
	 * @param msg
	 */
	private void serverMessage(String msg) { //an alias to avoid typing so much!
		message_to.message(msg);
	}

	@Override
	public void sendResponseText(String[] text, OutputStreamWriter output) {

		System.out.println("ElementalHttpServer:sendResponseText");
		
		try {
	    	
			// Die Server-Antwort an den Client 
			output.write(construct_http_header(200, 5));
//			output.writeBytes(construct_http_header(200, 5));
			
			for(String s : text)
			{
				output.write(s+"\n");

				// Infotext fuer WebserverGui
				serverMessage("message to client:"+s);
			}

	        // Writer leerschreiben und schliessen
			output.flush();
	    	output.close();
	    }

	    catch (Exception e) {e.printStackTrace();}
	}

	  //this method makes the HTTP header for the response
	  //the headers job is to tell the browser the result of the request
	  //among if it was successful or not.
		private String construct_http_header(int return_code, int file_type) {
			
//			String s = "HTTP/1.0 ";
//	    //you probably have seen these if you have been surfing the web a while
//			switch (return_code) {
//				case 200:
//					s = s + "200 OK";
//					break;
//				case 400:
//					s = s + "400 Bad Request";
//					break;
//				case 403:
//					s = s + "403 Forbidden";
//					break;
//				case 404:
//				  s = s + "404 Not Found";
//				  break;
//				case 500:
//					s = s + "500 Internal Server Error";
//					break;
//				case 501:
//					s = s + "501 Not Implemented";
//					break;
//			}
//
//			s = s + "\r\n"; //other header fields,
//			s = s + "Connection: close\r\n"; //we can't handle persistent connections
//			s = s + "Server: SimpleHTTPtutorial v0\r\n"; //server name
//			s = s + "Content-Type: text/html\r\n";
//			s = s + "\r\n"; //this marks the end of the httpheader
//			// and the start of the body
//			// ok return our newly created header!
//	    
//			return s;
			
			return "";
		}

	
//    public static void main(String[] args) throws Exception {
//    	
//    	args = new String[] { new File("").toString() };
//    	
//        if (args.length < 1) {
//            System.err.println("Please specify document root directory");
//            System.exit(1);
//        }
//        Thread t = new RequestListenerThread(8080, args[0]);
//        t.setDaemon(false);
//        t.start();
//    }
    
}
