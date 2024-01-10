package swin.metrictool.intentions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import swin.metrictool.ExtractVersionException;
import swin.metrictool.ExtractVersionMetrics;
import swin.metrictool.ExtractedVersionData;
import swin.metrictool.MetricsExtractEvent;
import swin.metrictool.MetricsExtractionEventListener;

public class ExtractVersionMetricsIntention implements MetricsExtractionEventListener
{
	private int eventCount;

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ExtractVersionMetricsIntention.class);
	}

	@Test(expected=ExtractVersionException.class)
	public void doesItThrowExceptionForWrongFilePath() throws ExtractVersionException{
		String[] files = new String[]{"lib/junit-4.1.jar", "lib/nunit-4.1.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		extract.getMetrics(files);	
	}
	
	@Test
	public void specifyThatWeCanExtractMetrics() throws ExtractVersionException
	{
		String[] files = new String[]{"lib/junit-4.1.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(files);
		assertNotSame("Jar should contain at least one class", actual.getTotalClasses(), 0);

		actual.setSequenceNumber(524);
		actual.setVersionDescription("JUnit Sample Version");
		actual.setVersionName("JUnit");
		actual.setVersionNumber("4.1");
		actual.setVersionReleaseDate("15 October 2006");

		assertEquals("Sequence Number", actual.getSequenceNumber(), 524);

		assertEquals("Version Description", actual.getVersionDescription(),
				"JUnit Sample Version");
		assertEquals("Version Name", actual.getVersionName(), "JUnit");
		assertEquals("Version Number", actual.getVersionNumber(), "4.1");
		assertEquals("Version Release Date", actual.getVersionReleaseDate(),
				"15 October 2006");

		assertEquals("TotalByteCode", actual.getTotalByteCode(), 9177);
		assertEquals("TotalClasses", actual.getTotalClasses(), 96);
		assertEquals("TotalInstanceVariables", actual.getTotalInstanceVariables(), 1256);
		assertEquals("TotalMethodCount", actual.getTotalMethodCount(), 560);
		assertEquals("TotalPackageCount", actual.getTotalPackageCount(), 11);
		assertEquals("TotalPrivateMethodCount", actual.getTotalPrivateMethodCount(), 56);
	}
	
	@Test
	public void specifyThatWeCanExtractMetricsFromJarandClasses() throws ExtractVersionException
	{
		String[] files = new String[]{"lib/junit-4.1.jar","classes/swin/metrictool/OperationHandler.class","classes/swin/metrictool/ChartMaker.class"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(files);
		assertNotSame("Jar should contain at least one class", actual.getTotalClasses(), 0);

		actual.setSequenceNumber(524);
		actual.setVersionDescription("JUnit Sample Version");
		actual.setVersionName("JUnit");
		actual.setVersionNumber("4.1");
		actual.setVersionReleaseDate("15 October 2006");

		assertEquals("Sequence Number", actual.getSequenceNumber(), 524);

		assertEquals("Version Description", actual.getVersionDescription(),
				"JUnit Sample Version");
		assertEquals("Version Name", actual.getVersionName(), "JUnit");
		assertEquals("Version Number", actual.getVersionNumber(), "4.1");
		assertEquals("Version Release Date", actual.getVersionReleaseDate(),
				"15 October 2006");

		assertEquals("TotalByteCode", actual.getTotalByteCode(), 10653 );
		assertEquals("TotalClasses", actual.getTotalClasses(), 98);
		assertEquals("TotalInstanceVariables", actual.getTotalInstanceVariables(), 1349);
		assertEquals("TotalMethodCount", actual.getTotalMethodCount(), 581);
		assertEquals("TotalPackageCount", actual.getTotalPackageCount(), 12);
		assertEquals("TotalPrivateMethodCount", actual.getTotalPrivateMethodCount(), 58);
	}

	@Test
	public void specifyThatExtractVersionMetricsSendsEvents() throws ExtractVersionException
	{
		String[] files = new String[]{"lib/junit-4.1.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		// We register this class instance to receive events.
		// All fired events will be sent to the MetricsExtractionEvent defined below...
		extract.addEventListener(this);
		this.eventCount = 0;
		int expectedEventCount = 96;
		extract.getMetrics(files);
		assertEquals("An unexpected number of events where fired.", expectedEventCount,
				eventCount);
	}
	
	@Test
	public void specifyThatExtractVersionMetricsSendsEventsRemoveMethodworks() throws ExtractVersionException
	{
		String[] files = new String[]{"lib/junit-4.1.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		// We register this class instance to receive events.
		// All fired events will be sent to the MetricsExtractionEvent defined below...
		extract.addEventListener(this);
		this.eventCount = 0;
		int expectedEventCount = 96;
		extract.getMetrics(files);
		assertEquals("An unexpected number of events where fired.", expectedEventCount,
				eventCount);
		extract.removeEventListener(this);
		assertEquals("This has listen to events after being removed.", expectedEventCount,
				eventCount);
	}

	public void metricsExtractionEventOccured(MetricsExtractEvent event)
	{
		// Count each event as it is fired..
		this.eventCount++;
		// To view each analysed class name, uncomment the following two lines...
		// ExtractedClassData source = (ExtractedClassData)event.getSource();
		// System.out.println(source.getClassName());
	}


}
