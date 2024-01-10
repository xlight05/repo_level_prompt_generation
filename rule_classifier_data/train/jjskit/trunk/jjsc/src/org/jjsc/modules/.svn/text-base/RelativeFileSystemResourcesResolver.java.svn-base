package org.jjsc.modules;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.jjsc.compiler.javascript.ResourcesResolver;
/**
 * Resolves resources using relative paths and some filesystem root.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class RelativeFileSystemResourcesResolver implements ResourcesResolver {
	private File root;
	
	public RelativeFileSystemResourcesResolver(File root) {
		this.root = root;
	}

	public Reader getResource(String name) throws IOException {
		return new FileReader(new File(root, name));
	}
}
