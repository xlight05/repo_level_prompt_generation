package org.jjsc.modules;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jjsc.LoadModuleException;
import org.jjsc.Module;
import org.jjsc.utils.AssertUtils;
/**
 * This module loads compilation units from the jar file so that the module  
 * is represented as single jar.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class JarModule extends Module {
	private JarFile jarFile;

	public JarModule(JarFile jarFile) {
		AssertUtils.assertNotNull(jarFile, "Jar file is NULL");
		this.jarFile = jarFile;
	}

	@Override
	public String getName() {
		return jarFile.getName();
	}

	@Override
	protected List<URL> getAllCompilationUnits() throws LoadModuleException {
		List<URL> result = new LinkedList<URL>();
		Enumeration<JarEntry> entities = jarFile.entries();
		while(entities.hasMoreElements()) {
			JarEntry next = entities.nextElement();
			if(!next.isDirectory()&&
				next.getName().endsWith(CLASS_FILE_EXT)){
				result.add(createUrlForEntry(next));
			}
		}
		return Collections.unmodifiableList(result);
	}

	private URL createUrlForEntry(JarEntry entry) throws LoadModuleException {
		StringBuilder url = new StringBuilder();
		try {
			url.append("jar://file://");
			url.append(jarFile.getName());
			url.append('!');
			url.append(entry.getName());
			return new URL(url.toString());
		}
		catch(MalformedURLException ex){
			throw new LoadModuleException(ex);
		}
	}

}
