package de.virtualprocessmanagement.processing;

import java.io.File;

import de.virtualprocessmanagement.tools.FileHandler;

public class ProcessMap {
 
	String[][] mapAsStringArray = null;
	
	public ProcessMap(String filename) {
		
		FileHandler fh = new FileHandler();
		
		String[] strArr = fh.getTextLines(new File(filename));
		String[] swap = null;
		
		mapAsStringArray = new String[strArr.length][(strArr[0].split(";")).length];
		
		for(int i=0; i<strArr.length; i++) {
			
			swap = strArr[i].split(";");
			
			for(int j=0; j<swap.length; j++)
				mapAsStringArray[i][j] = swap[j];
		}
		
		for(String[] arr : mapAsStringArray) {
			for(String str : arr) 
				System.out.print(str+" ");
		
			System.out.println("");
		}
//		mapAsStringArray = fh.getTextLines(new File(filename));
	}
}
