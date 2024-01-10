package swin.metrictool;

import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Updated to use annotated style
 */
public class OperationHandlerTest
{

	// Allow old style test runners
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(OperationHandlerTest.class);
	}

	private ArrayList emptyList;

	/**
	 * Sets up the test fixture. (Called before every test case method.)
	 */
	@Before
	public void setUp()
	{
		this.emptyList = new ArrayList();
	}

	/**
	 * Tears down the test fixture. (Called after every test case method.)
	 */
	@After
	public void tearDown()
	{
		this.emptyList = null;
	}

	@Test
	public void testSomeBehavior()
	{
		Assert.assertEquals("Array should be empty", this.emptyList.size(), 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testForException()
	{
		throw new IndexOutOfBoundsException("Should raise an IndexOutOfBoundsException");
	}
}
