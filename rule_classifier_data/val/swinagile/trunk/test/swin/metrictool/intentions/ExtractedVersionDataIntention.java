package swin.metrictool.intentions;

import java.util.HashMap;
import java.util.Vector;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

import swin.metrictool.ExtractVersionException;
import swin.metrictool.ExtractVersionMetrics;
import swin.metrictool.ExtractedClassData;
import swin.metrictool.ExtractedVersionData;

public class ExtractedVersionDataIntention
{

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ExtractedVersionDataIntention.class);
	}

	@Test
	public void testCalculateNumberOfPackages()
	{
		String classOneName = "swin/metrictool/ClassOne";
		String classTwoName = "swin/metrictool/ClassTwo";
		String classThreeName = "ClassThree";
		ExtractedVersionData evd = new ExtractedVersionData();
		ExtractedClassData classOne = new ExtractedClassData(0, 0, 0, 0, classOneName,
				new Vector<String>());
		ExtractedClassData classTwo = new ExtractedClassData(0, 0, 0, 0, classTwoName,
				new Vector<String>());
		ExtractedClassData classThree = new ExtractedClassData(0, 0, 0, 0, classThreeName,
				new Vector<String>());
		evd.addToClassList(classOne);
		evd.addToClassList(classTwo);
		evd.addToClassList(classThree);
		int expected = 2; // swin.metrictool & default packages
		int actual = evd.getTotalPackageCount();
		Assert.assertEquals("Package count does not match", actual, expected);
	}

	@Test
	public void testFanInCalculation() throws ExtractVersionException
	{
		ExtractVersionMetrics evm = new ExtractVersionMetrics();
		String[] files = {"lib/junit-4.1.jar"};
		ExtractedVersionData evd = evm.getMetrics(files);

		HashMap<Integer, Integer> actualFos = new HashMap<Integer, Integer>();
		actualFos.put(new Integer(15), new Integer(5));
		actualFos.put(new Integer(4), new Integer(7));
		actualFos.put(new Integer(30), new Integer(1));
		actualFos.put(new Integer(8), new Integer(3));
		actualFos.put(new Integer(11), new Integer(3));
		actualFos.put(new Integer(18), new Integer(1));
		actualFos.put(new Integer(3), new Integer(8));
		actualFos.put(new Integer(7), new Integer(2));
		actualFos.put(new Integer(12), new Integer(4));
		actualFos.put(new Integer(22), new Integer(1));
		actualFos.put(new Integer(2), new Integer(2));
		actualFos.put(new Integer(13), new Integer(4));
		actualFos.put(new Integer(9), new Integer(5));
		actualFos.put(new Integer(21), new Integer(1));
		actualFos.put(new Integer(6), new Integer(11));
		actualFos.put(new Integer(1), new Integer(10));
		actualFos.put(new Integer(20), new Integer(1));
		actualFos.put(new Integer(14), new Integer(2));
		actualFos.put(new Integer(10), new Integer(4));
		actualFos.put(new Integer(5), new Integer(12));
		actualFos.put(new Integer(0), new Integer(9));

		HashMap<Integer, Integer> actualFis = new HashMap<Integer, Integer>();
		actualFis.put(new Integer(2), new Integer(13));
		actualFis.put(new Integer(4), new Integer(4));
		actualFis.put(new Integer(13), new Integer(2));
		actualFis.put(new Integer(9), new Integer(2));
		actualFis.put(new Integer(1), new Integer(33));
		actualFis.put(new Integer(3), new Integer(6));
		actualFis.put(new Integer(14), new Integer(1));
		actualFis.put(new Integer(7), new Integer(1));
		actualFis.put(new Integer(10), new Integer(2));
		actualFis.put(new Integer(5), new Integer(1));
		actualFis.put(new Integer(0), new Integer(31));

		HashMap<Integer, Integer> expectedFos = evd.getFanOutSummmary();
		Assert.assertEquals("Fan-Out Summary does not reflect Jar file.", actualFos,
				expectedFos);
		HashMap<Integer, Integer> expectedFis = evd.getFanInSummary();
		Assert.assertEquals("Fan-In Summary does not reflect Jar file.", actualFis,
				expectedFis);
	}
}
