package nl.kvk.vt.monitoring.gc;

import java.io.IOException;
import java.io.Reader;

public class TestUtils {
	public static String readAll(Reader reader) {
		StringBuilder builder = new StringBuilder();
		int c;
		try {
			while ((byte)(c = reader.read()) != -1) {
				builder.append((char) c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();

	}
}
