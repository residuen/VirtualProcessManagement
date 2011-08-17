package de.virtualprocessmanagement.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class FileHandler
{
	public String getTextFile(File file)
	{
		StringBuffer str = new StringBuffer();

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNext()) {
				str.append(scanner.nextLine() + "\n");
			}

			// System.out.println(str.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return str.toString().substring(0, str.length() - 1).trim();

	}
	
	public String[] getTextLines(File file)
	{
		return getTextFile(file).split("\n");
	}
	
	/**
	 * Returns a vector, containing only files-objects with directorys
	 * @param folderName
	 * @return
	 */
	public File[] getFolderList(String folderName) {
		
		Vector<File> files = new Vector<File>();
		File file = new File(folderName);
		
		for(File f : file.listFiles())	// walking thrue the filelist-array
			if(f.isDirectory())			// Testing if is directory ...
				files.add(f);			// ... if is dir, add to return-vector
		
		return (File[])files.toArray();
	}
	
}
