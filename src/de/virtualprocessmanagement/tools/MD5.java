package de.virtualprocessmanagement.tools;

import java.security.*;
import java.util.Scanner;

/*
Program: MD5.java
This program generates MD5-Hashes

Copyright (C) 2010 Karsten Bettray
Version: v0.1f

This program is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library;
if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA *
*/

/**
 * <u>MD5-Klasse, zum erzeugen von MD5-Hashes aus Zeichenketten</u><br><br>
 * <b>Class:</b> MD5<br><br>
 * <b>Java-Version:</b> 1.5x<br><br>
 * <b>&copy; Copyright:</b> Karsten Bettray - 2010<br><br>
 * <b>License:</b> GPL2.0<br>
  * @author Karsten Bettray (Universit&auml;t Duisburg-Essen)<br><br>
 * @version 0.1<br>
 *
 */
public class MD5
{
	private String text = null;
	private String hash = null;
	
	/**
	 * <u>Konstruktor mit &Uuml;bergabe der zu verifizierenden Zeichenkette</u>
	 * @param text
	 */
	public MD5(String text)
	{
		this.text = text;
	}
	
	/**
	 * <u>Zur&uuml;ckgabe des MD5-Hashes, bei Initialisierter Membervariable 'text'</u>
	 * @return
	 */
	public String getMD5()
	{
		if(text==null)
			return null;
		else
			return makeMD5();
	}
	
	/**
	 * <u>Zur&uuml;ckgabe des MD5-Hashes</u>
	 * @param text
	 * @return
	 */
	public String getMD5(String text)
	{
		this.text = text;
		
		if(text==null)
			return null;
		else
			return makeMD5();
	}
	
	/**
	 * <u>MD5-Hash erzeugen</u>
	 * @return
	 */
	private String makeMD5()
	{
		MessageDigest md = null;
		byte[] encryptMsg = null;

		try {
			md = MessageDigest.getInstance( "MD5" );		// getting a 'MD5-Instance'
			encryptMsg = md.digest(text.getBytes());		// solving the MD5-Hash
		}catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm Exception!");
		}
			
		String swap="";										// swap-string for the result
		String byteStr="";									// swap-string for current hex-value of byte
		StringBuffer strBuf = new StringBuffer();
		
		for(int i=0; i<=encryptMsg.length-1; i++) {
			
			byteStr = Integer.toHexString(encryptMsg[i]);	// swap-string for current hex-value of byte
			
			switch(byteStr.length()) {
			case 1:											// if hex-number length is 1, add a '0' before
				swap = "0"+Integer.toHexString(encryptMsg[i]);
				break;
				
			case 2:											// correct hex-letter
				swap = Integer.toHexString(encryptMsg[i]);
				break;
				
			case 8:											// get the correct substring
				swap = (Integer.toHexString(encryptMsg[i])).substring(6,8);
				break;
			}
			strBuf.append(swap);							// appending swap to get complete hash-key
		}
		hash = strBuf.toString();							// String with the MD5-Hash
		
		return hash;										// returns the MD5-Hash
	}
	
	/**
	 * <u>Setzt den Text, aus dem der MD5-Hash ermittelt werden soll</u>
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * <u>Gibt den Text, aus dem der MD5-Hash ermittelt werden soll zur&uuml;ck</u>
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * <u>Gibt den TExt des HASH-Keys zurueck</u>
	 * @return Returns the hash.
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * <u>main-Methode</u>
	 * @param args
	 */
	public static void main(String[] args)
	{
//		MD5 test = new MD5("Test");			// Testen der Umwandlung des Strings 'Test'
//		System.out.println("Test->"+test.getMD5());
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Geben Sie einen Text ein:");
		MD5 test2 = new MD5(scan.nextLine());
		System.out.println(test2.getText()+"->"+test2.getMD5());
		
	}
}

// MD5-Hash of "Test": 0cbc6611f5540bd0809a388dc95a615b