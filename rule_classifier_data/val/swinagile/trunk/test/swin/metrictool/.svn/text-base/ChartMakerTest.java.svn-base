package swin.metrictool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

public class ChartMakerTest
{

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ChartMakerTest.class);
	}

	@Test
	public void testCreate_FanChart() throws FileNotFoundException, IOException
	{
		ChartMaker cm = new ChartMaker();
		try
		{
			cm.create_FanChart(null, null, -1, -1, -1);
			Assert.fail();
		}
		catch (NullPointerException e)
		{
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testXYResolution()
	{
		ChartMaker cm = new ChartMaker();
		Assert.assertEquals(cm.get_chartXResolution(), 800);
		Assert.assertEquals(cm.get_chartYResolution(), 600);
		cm.set_chartXResolution(450);
		cm.set_chartYResolution(50);
		Assert.assertEquals(cm.get_chartXResolution(), 450);
		Assert.assertEquals(cm.get_chartYResolution(), 50);
	}

	@Test
	public void TestCreateChart()
	{
		Vector<ExtractedVersionData> evdVect = new Vector<ExtractedVersionData>();
		for (int k = 1; k < 6; k++)
		{
			ExtractedVersionData evd = new ExtractedVersionData();
			evd.setSequenceNumber(k);
			Vector<String> fanOutList = null;
			for (int i = 1; i < 200 * Math.random() * k; i++)
			{
				fanOutList = new Vector<String>();
				int a = (int) (Math.random() * 10 + k);
				for (int j = 0; j < a; j++)
					fanOutList.add("Class" + i);
				ExtractedClassData cd = new ExtractedClassData(i, 3 * i, 2 * i, 5 * i,
						"TestClass" + i, fanOutList);
				evd.addToClassList(cd);
			}
			evdVect.add(evd);
		}
		ChartMaker cm = new ChartMaker();

		cm.set_VersionData(evdVect);
		cm.create_FanChart("testFanOut.jpg", FanType.FanOut, 0, 4, 1);
		cm.create_GrowthChart("testGrowth.jpg", GrowthType.Classes, 0, 4, 1);

		File f1 = new File("testFanOut.jpg");
		Assert.assertTrue(f1.exists());
		File f2 = new File("testGrowth.jpg");
		Assert.assertTrue(f2.exists());

	}

}
