import java.io.*;

final class StringInputStream extends InputStream {
  
  private final String s;
  private int cur;

  StringInputStream(String s) {this.s = s;}

  public int read() throws IOException {
    if (cur >= s.length()) return -1;
    return s.charAt(cur++);
  }
}