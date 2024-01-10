import junit.framework.TestCase;

import java.io.*;
import java.util.*;

public class ParserTest extends TestCase {

  public void testAll() {
    File dir = new File("tests");
    List<File> q = new ArrayList<File>();
    q.add(dir);
    while (!q.isEmpty()) {
      File d = q.remove(0);
      for (File f : d.listFiles()) {
        if (f.isDirectory()) {
          q.add(f);
        } else if (f.getName().endsWith(".j")) {
          try {
            runTest(f);
          } catch (Throwable t) {
            fail(t.getMessage());
          }
        }
      }
    }
  }
  
  private void runTest(File f) throws Exception {System.out.println(f);
    Parser p = new Parser(new FileInputStream(f));
    ASTStart n = p.Start();
    assertEquals(f + " should pass", true, n != null);
  }

  protected void setUp() {

  }

  protected void tearDown() {

  }
}