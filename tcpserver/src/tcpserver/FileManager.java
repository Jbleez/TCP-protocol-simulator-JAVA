
package tcpserver;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
	

	static byte[] dataOfFile;
	static ArrayList<byte[]> fileDataList = new ArrayList<byte[]>();
	static ArrayList<String> fileNameList = new ArrayList<String>();
	static File f;
	
	public FileManager(){

	}
	public static void readFile(String filename, String nameOfFile)
	{
		try
		{
			// Create a new file.
			File f = new File(filename);
			// Create a file input stream.
			FileInputStream fis = new FileInputStream(f);
			// Create a buffer for the file.
			dataOfFile = new byte[(int)f.length()];
			// Read the contents of the file into the buffer.
			fis.read(dataOfFile, 0, (int)f.length());
			// Add the file name to the list.
			fileNameList.add(nameOfFile);
			// Add that file to the list.
			fileDataList.add(dataOfFile);
			// Output the length of the file.
			System.out.println("File path:" + filename);
			System.out.println("File name: " + nameOfFile);
			System.out.println("File size: " + dataOfFile.length + " bytes.");
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			e.getMessage();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
