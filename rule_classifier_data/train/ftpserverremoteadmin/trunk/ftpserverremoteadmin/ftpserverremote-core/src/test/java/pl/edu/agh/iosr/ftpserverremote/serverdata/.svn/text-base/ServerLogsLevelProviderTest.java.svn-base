/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.agh.iosr.ftpserverremote.serverdata;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Level;

/**
 *
 * @author Tomasz Jadczyk
 */
public class ServerLogsLevelProviderTest extends TestCase {
    
    public ServerLogsLevelProviderTest(String testName) {
        super(testName);
    }

    public static Test suite() {
	TestSuite suite = new TestSuite(ServerLogsLevelProviderTest.class);
	return suite;
    }

    /**
     * Test of getLogLevels method, of class ServerLogsLevelProvider.
     */
    public void notestGetLogLevels() {
        System.out.println("getLogLevels");
        String[] expResult = null;
        String[] result = ServerLogsLevelProvider.getLogLevels();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLog4jLevel method, of class ServerLogsLevelProvider.
     */
    public void notestGetLog4jLevel() {
        System.out.println("getLog4jLevel");
        String levelName = "";
        Level expResult = null;
        Level result = ServerLogsLevelProvider.getLog4jLevel(levelName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUtilLoggingLevel method, of class ServerLogsLevelProvider.
     */
    public void notestGetUtilLoggingLevel() {
        System.out.println("getUtilLoggingLevel");
        String levelName = "";
        java.util.logging.Level expResult = null;
        java.util.logging.Level result = ServerLogsLevelProvider.getUtilLoggingLevel(levelName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isActiveLevel method for java.util.logging, of class ServerLogsLevelProvider.
     */
    public void testIsActiveJavaUtilLoggingLevel() {
        System.out.println("isActiveLevel for java util logging");
        String currentLevel = "NONE";
        java.util.logging.Level level = null;
        boolean expResult = false;
        boolean result = ServerLogsLevelProvider.isActiveLevel(currentLevel, level);
        assertEquals(expResult, result);
        System.out.println("NONE");
        
        currentLevel = "INFO";
        level = java.util.logging.Level.INFO;
        expResult = true;
        result = ServerLogsLevelProvider.isActiveLevel(currentLevel, level);
        assertEquals(expResult, result);
        System.out.println("INFO, INFO");
        
        level = java.util.logging.Level.SEVERE;
        expResult = true;
        result = ServerLogsLevelProvider.isActiveLevel(currentLevel, level);
        assertEquals(expResult, result);
        System.out.println("INFO, SEVERE");
        
        level = java.util.logging.Level.CONFIG;
        expResult = false;
        result = ServerLogsLevelProvider.isActiveLevel(currentLevel, level);
        assertEquals(expResult, result);
        System.out.println("INFO, CONFIG");
        
        level = java.util.logging.Level.FINEST;
        expResult = false;
        result = ServerLogsLevelProvider.isActiveLevel(currentLevel, level);
        assertEquals(expResult, result);
        System.out.println("INFO, FINEST");
    }

    /**
     * Test of isActiveLevel method, of class ServerLogsLevelProvider.
     */
    public void notestIsActiveLevel() {
	System.out.println("isActiveLevel");
	String currentLevel = "";
	java.util.logging.Level level = null;
	boolean expResult = false;
	boolean result = ServerLogsLevelProvider.isActiveLevel(currentLevel, level);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getLoggingLevelName method, of class ServerLogsLevelProvider.
     */
    public void notestGetLoggingLevelName() {
	System.out.println("getLoggingLevelName");
	java.util.logging.Level level = null;
	String expResult = "";
	String result = ServerLogsLevelProvider.getLoggingLevelName(level);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

}
