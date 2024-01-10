package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Writer;

import org.jjsc.JJSVM;
/**
 * Writes current version as assignment operator.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class VersionTagProcessor implements TagProcessor {

	public void process(String parameters, ResourcesResolver resolver,
			Writer result, String fileName, int line) throws IOException {
		result.write('=');
		result.write('"');
		result.write(JJSVM.VERSION);
		result.write('"');
	}
}
