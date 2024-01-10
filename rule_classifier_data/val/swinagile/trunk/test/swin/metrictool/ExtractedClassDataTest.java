package swin.metrictool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

import swin.metrictool.ExtractedClassData;

public class ExtractedClassDataTest
{

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ExtractedClassDataTest.class);
	}

	@Test
	public void canCalculatePackageNameForClass() throws FileNotFoundException, IOException
	{
		String className = "swin/metrictool/ClassMetricExtractorIntention";
		String expected = "swin/metrictool";
		ExtractedClassData ecd = new ExtractedClassData(0, 0, 0, 0, className,
				new Vector<String>());
		ecd.setClassName(className);
		String actual = ecd.getPackageName();
		Assert.assertEquals("Package Name does not match", expected, actual);
	}
}
