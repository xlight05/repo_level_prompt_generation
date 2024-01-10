package swin.metrictool.intentions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import swin.metrictool.FileSystemUtilities;

public class FileSystemUtilitiesIntention
{
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(FileSystemUtilitiesIntention.class);
	}
	
	static final String sampleDirectory = "sample.dir";
	static final String sampleFileName = "sample.file";
	static final String sampleFileData = "sample.file.data";
	
	private File sampleFile;
	private File sampleDir;

	@Before
	public void setup()
	{
		sampleDir = new File(sampleDirectory);
		sampleFile = new File(sampleFileName);
	}

	@After
	public void tearDown()
	{		
		sampleDir.delete();		
		sampleFile.delete();
	}
	
	@Test
	public void specifyThatWeCanWriteFiles() throws Exception
	{
		FileSystemUtilities.writeFile(sampleFileName, new StringBuffer(sampleFileData));
		assertTrue("Sample file was not created.", sampleFile.exists());
		assertTrue("Sample file is not a file.", sampleFile.isFile());
	}

	@Test
	public void specifyThatWeCanReadFiles() throws IOException
	{
		createSampleFile();
		// Buffer will be terminated by a newline.
		StringBuffer buffer = FileSystemUtilities.readFile(sampleFileName);
		String actual = buffer.toString();
		String expected = sampleFileData; 
		// Use starts with to ignore line termination.
		assertTrue("Sample file data does not match.", actual.startsWith(expected));
	}

	@Test
	public void specifyThatWeCanMakeDirectories()
	{
		FileSystemUtilities.makeDirectory(sampleDirectory);
		assertTrue("Sample directory was not created.", sampleDir.exists());
		assertTrue("Sample directory is not a directory.", sampleDir.isDirectory());
	}

	@Test
	public void specifyThatWeCanCheckDirectoriesExist()
	{
		sampleDir.mkdir();
		FileSystemUtilities.exists(sampleDirectory);
	}
	
	private void createSampleFile() throws IOException
	{
		sampleFile.createNewFile();
		PrintWriter writer = new PrintWriter(sampleFile);
		writer.println(sampleFileData);
		writer.close();
	}

}
