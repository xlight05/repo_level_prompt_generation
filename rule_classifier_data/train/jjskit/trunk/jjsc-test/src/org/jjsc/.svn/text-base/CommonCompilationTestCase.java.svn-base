package org.jjsc;

import java.io.File;

import org.jjsc.utils.Log;
import org.jjsc.utils.Log.LogLevel;
/**
 * TODO: this is temporary testcase - please remove after all testcases will be written
 * @author alex.bereznevatiy@gmail.com
 *
 */
@Deprecated
public class CommonCompilationTestCase extends CommonJJSTestCase {
	
	public void testSimple() throws JJSException{
		File target = new File("./");
		JJSC.compile(target, "./bin/org/jjsc/mock/simple");
	}
	
	public void testOperations() throws JJSException{
		File target = new File("./");
		JJSC.compile(target, "./bin/org/jjsc/mock/operations");
	}
	
	public void testConditions() throws JJSException{
		Log.setDefaultLevel(LogLevel.DEBUG);
		File target = new File("./");
		JJSC.compile(target, "./bin/org/jjsc/mock/conditions");
		Log.setDefaultLevel(LogLevel.INFO);
	}
}
