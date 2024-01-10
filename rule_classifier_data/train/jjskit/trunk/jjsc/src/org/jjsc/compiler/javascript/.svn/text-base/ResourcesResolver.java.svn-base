package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Reader;

/**
 * This interface describes resolver of javascript resources by theirs names.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public interface ResourcesResolver {
	/**
	 * Gets a resource reader for the resource with name specified.
	 * Client code shoouldn't care about reader closing - it will be closed automatically when necessary.
	 * @param resource name
	 * @return a reader for resource using resource's name.
	 * @throws IOException in case of troubles
	 */
	Reader getResource(String name) throws IOException;
}
