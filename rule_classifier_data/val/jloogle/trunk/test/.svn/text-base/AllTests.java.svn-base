// -*- java -*-

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestSuite that runs all the tests
 */
public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("JLoogle Tests");
    Class[] clss = {ClassToStringTest.class,
ParserTest.class,
    };
		for (Class cls : clss) suite.addTestSuite(cls);
		return suite;
	}
}