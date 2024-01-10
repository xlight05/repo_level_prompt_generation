package swin.metrictool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystemUtilities
{

	public static StringBuffer readFile(String filename)
	{
		StringBuffer returnBuffer = null;

		// declared here only to make visible to finally clause
		BufferedReader input = null;
		try
		{
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			input = new BufferedReader(new FileReader(filename));
			String line = null; // not declared within while loop
			/*
			 * readLine is a bit quirky : it returns the content of a line MINUS the newline.
			 * it returns null only for the END of the stream. it returns an empty String if
			 * two newlines appear in a row.
			 */
			while ((line = input.readLine()) != null)
			{
				returnBuffer.append(line);
				returnBuffer.append(System.getProperty("line.separator"));
			}
		}
		catch (FileNotFoundException ex)
		{
			//ex.printStackTrace();
		}
		catch (IOException ex)
		{
			//ex.printStackTrace();
		}
		finally
		{
			try
			{
				// Flush and close both "input" and its underlying FileReader
				if (input != null)
				input.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return returnBuffer;
	}

	public static void writeFile(String filename, StringBuffer fileText) throws Exception
	{
		FileOutputStream fos = null;
		PrintWriter pWriter = null;
		try
		{
			fos = new FileOutputStream(filename, false);
			pWriter = new PrintWriter(fos, true);
			pWriter.println(fileText.toString());
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (pWriter != null) pWriter.close();
			if (fos != null) try
			{
				fos.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean makeDirectory(String directory)
	{
		File dir = new File(directory);
		boolean success = dir.mkdirs();
		return success;
	}

	public static boolean exists(String directory)
	{
		File dir = new File(directory);
		return dir.exists();
	}
}
