package swin.metrictool;

import java.util.Vector;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest
{
	// Allow old style test runners
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ProjectTest.class);
	}

	private static final String shortName = "xpp3";
	private static final String creationDate = "2005-08-11";
	private static final String description = "lib/xpp3_min-1.1.3.4.O.jar";
	private static final String directory = "directory\test";
	private static final Vector<ExtractedVersionData> versions = new Vector<ExtractedVersionData>();

	private Project _project;

	@Before
	public void setUp()
	{
		// Use Setters:
		_project = new Project();
		_project.setShortName(shortName);
		_project.setCreationDate(creationDate);
		_project.setDescription(description);
		_project.setDirectory(directory);
		_project.setVersions(versions);
	}

	@After
	public void tearDown()
	{
		_project = null;
	}

	@Test
	public void testThatShortNameWasSet()
	{
		Assert.assertEquals("Test shortName initialisation.", shortName,
				_project.getShortName());
	}

	@Test
	public void testThatCreationDateWasSet()
	{
		Assert.assertEquals("Test creationDate initialisation.", creationDate,
				_project.getCreationDate());
	}

	@Test
	public void testThatDescriptionWasSet()
	{
		Assert.assertEquals("Test description initialisation.", description,
				_project.getDescription());
	}

	@Test
	public void testThatDirectoryWasSet()
	{
		Assert.assertEquals("Test directory initialisation.", directory,
				_project.getDirectory());
	}

	@Test
	public void testThatVersionsWasSet()
	{
		Assert.assertEquals("Test versions initialisation.", versions, _project.getVersions());
	}

	@Test
	public void testThatSetDetailsInitialisesFields()
	{
		_project.setDetails("new shortName", "new description", "new creationDate");
		Assert.assertEquals("Test shortName initialisation.", "new shortName",
				_project.getShortName());
		Assert.assertEquals("Test description initialisation.", "new description",
				_project.getDescription());
		Assert.assertEquals("Test creationDate initialisation.", "new creationDate",
				_project.getCreationDate());
	}
}
