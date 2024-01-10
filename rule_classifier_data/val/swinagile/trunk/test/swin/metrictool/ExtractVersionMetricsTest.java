package swin.metrictool;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

public class ExtractVersionMetricsTest
{
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ExtractVersionMetricsTest.class);
	}

	@Test
	public void testGivesEmptyMetricsFileForNoFiles() throws ExtractVersionException
	{
		int expected = 0;
		String[] files = new String[0];
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(files);
		Assert.assertEquals("Expected empty set of metrics data for empty file list",
				expected, actual.getTotalClasses());
	}

	@Test
	public void testGivesEmptyMetricsForNullValue() throws ExtractVersionException
	{
		int expected = 0;
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(null);
		Assert.assertEquals("Expected empty set of metrics data for null file list",
				expected, actual.getTotalClasses());
	}

	@Test
	public void testNonClassAndJarFilesShouldBeIgnored() throws ExtractVersionException
	{
		String[] files = new String[]{"<not a jar file>", "<not a class file>"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		extract.getMetrics(files);
		Assert.assertTrue(
				"Should NOT throw exception because files do not end in .jar or .class", true);
	}

	@Test(expected = ExtractVersionException.class)
	public void testThrowsExceptionIfFileIsMissing() throws ExtractVersionException
	{
		String[] files = new String[]{"file.class", "file.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		// Should throw exception because file does not exist
		extract.getMetrics(files);
	}

	@Test
	public void testCanExtractMetricsFromJarFile() throws ExtractVersionException
	{
		String jar[] = {"lib/junit-4.1.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(jar);
		Assert.assertNotSame("Jar should contain at least one class",
				actual.getTotalClasses(), 0);
	}

	@Test
	public void testCanExtractMetricsForTwoFiles() throws ExtractVersionException
	{

		String jar[] = {"lib/asm-all-3.0_RC1.jar", "lib/xstream-1.2.jar"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(jar);
		Assert.assertNotSame("Jar should contain at least one class",
				actual.getTotalClasses(), 0);
	}

	@Test
	public void testCanExtractMetricsFromClassFile() throws ExtractVersionException
	{
		String classfiles[] = {"db/samplefiles/MXParser.class"};
		ExtractVersionMetrics extract = new ExtractVersionMetrics();
		ExtractedVersionData actual = extract.getMetrics(classfiles);
		Assert.assertEquals("Metrics did not contain expected data.", 1,
				actual.getTotalClasses());
	}

	// valid jar without classes -> empty metrics

	// invalid jar should throw exception -> MetricsGenerationException

	// valid jar + invalid class file/s -> MetricsGenerationException

	// invalid class file -> MetricsGenerationException
}