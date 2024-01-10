import junit.framework.TestCase;

public class ClassToStringTest extends TestCase {

  public void testNull() {
    assertEquals("null", Util.toString(null));
  }

  public void testPrimitives() {
    assertEquals("void", Util.toString(void.class));
    assertEquals("int", Util.toString(int.class));
    assertEquals("boolean", Util.toString(boolean.class));
    assertEquals("short", Util.toString(short.class));
    assertEquals("char", Util.toString(char.class));
    assertEquals("double", Util.toString(double.class));
    assertEquals("float", Util.toString(float.class));
  }

  public void testObjects() {
    assertEquals("Main", Util.toString(Main.class));
    assertEquals("java.lang.String", Util.toString(String.class));
  }

  public void testArrays1Dims() {
    assertEquals("int[]", Util.toString(int[].class));
    assertEquals("Main[]", Util.toString(Main[].class));
    assertEquals("java.lang.String[]", Util.toString(String[].class));
  }

  public void testArrays2Dims() {
    assertEquals("int[][]", Util.toString(int[][].class));
    assertEquals("Main[][]", Util.toString(Main[][].class));
    assertEquals("java.lang.String[][]", Util.toString(String[][].class));
  }

  public void testArrays3Dims() {
    assertEquals("int[][][]", Util.toString(int[][][].class));
    assertEquals("Main[][][]", Util.toString(Main[][][].class));
    assertEquals("java.lang.String[][][]", Util.toString(String[][][].class));
  }

  protected void setUp() {

  }

  protected void tearDown() {

  }
}