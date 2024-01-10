package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Writer;

import org.jjsc.utils.Log;
/**
 * Warns about @TODO tags in the log.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class TodoTagProcessor implements TagProcessor {

	public void process(String parameters, ResourcesResolver resolver,
			Writer result, String fileName, int line) throws IOException {
		Log.warn("TODO in "+fileName+" at line "+line+": "+parameters.trim());
	}

}
