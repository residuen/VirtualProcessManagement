package de.virtualprocessmanagement.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;

public class CopyOfHTTPClientConnection {

//	public static final String ADRESS = "http://127.0.0.1:80";
	
	URL url = null;
	URL url2 = null;
	URLConnection con = null;
	URLConnection send2Server = null;
	Scanner scan = null;
	String hostname = "localhost";
	int port = 80;
	
	Socket socket = null;
	String path = "http://127.0.0.1/";
	private String playerPath = "Java.txt";
	InetAddress addr = null;
    BufferedWriter buffWriter = null;

//    ArrayList<Player> playerList = null; 
    
//	private HttpClient httpclient = new DefaultHttpClient();
	
	public CopyOfHTTPClientConnection(String hostname)
	{
		this.hostname = hostname;
		
		try {
			url = new URL("http://"+hostname+":"+port+"/"+playerPath);
		}
		catch (MalformedURLException e) { e.printStackTrace(); } 
		
		sendRequest();

		
//		try
//		{
//			send2Server = url.openConnection();
//			send2Server.setDoOutput(true);
//		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void sendRequest() {
		
//		HttpGet httpget = new HttpGet("http://"+hostname+"/"+playerPath);
//		HttpResponse response = null;
//		try {
//			response = httpclient.execute(httpget);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		HttpEntity entity = response.getEntity();
//		if (entity != null) {
//		    InputStream instream = null;
//			try {
//				instream = entity.getContent();
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    int l;
//		    byte[] tmp = new byte[2048];
//		    try
//		    {
//				while ((l = instream.read(tmp)) != -1) {
////					System.out.println("tmp="+l);
//				}
//				
////				System.out.println("tmp="+l);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		System.out.println(nextServerRequest());

	}
	
	public boolean sendPostData(boolean left, boolean right, boolean thrust, boolean fire)
	{		
		String data = " {\"actions\":{\"turnLeft\":"+left+",\"turnRight\":"+right+",\"thrust\":"+thrust+",\"fire\":"+fire+"}}\r\n";

		try {
		    // Send header
			buffWriter.write("POST "+path+" HTTP/1.0\r\n");
			buffWriter.write("Content-Length: "+data.length()+"\r\n");
			buffWriter.write("Content-Type: application/x-www-form-urlencoded\r\n");
			buffWriter.write("\r\n");

		    // Send data
			buffWriter.write(data);
			buffWriter.flush();

		    return true;

		} catch (Exception e) {
	
			/* Upsi */ return false;
		}
	}

	public String nextServerRequest()
	{
		String str = "";
		
		try {
			con = url.openConnection();
			
			scan = new Scanner(con.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNext())
		{
			str += scan.nextLine();
		}
		
		scan.close();
		
		return str;
	}
	
	public void close()
	{
		try {
			buffWriter.close();
			socket.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg0)
	{
//		new HTTPBytePacketConnection("localhost");
		new CopyOfHTTPClientConnection("127.0.0.1");
	}
}
