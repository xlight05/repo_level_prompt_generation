import java.lang.reflect.Array;

public final class Util {

  public static String toString(Class cls) {
    if (cls == null) return "null";
    StringBuilder sb = new StringBuilder();
    Class base = cls;
    while (base.isArray()) base = base.getComponentType();
    Package pkg = base.getPackage();
    if (pkg != null) {
      String pkgName = pkg.getName();
      if (!isEmpty(pkgName)) {
        sb.append(pkgName);
        sb.append(".");
      }
    }
    sb.append(cls.getSimpleName());
    return sb.toString();
  }

  /**
   * @return <code>true</code> if <code>s == null ||
   *         "".equals(s);</code>.
   */
 public static boolean isEmpty(String s) {
  return s == null || "".equals(s); }

}