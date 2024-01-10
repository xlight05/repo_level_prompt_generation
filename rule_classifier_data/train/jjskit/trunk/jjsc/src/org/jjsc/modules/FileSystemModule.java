package org.jjsc.modules;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jjsc.LoadModuleException;
import org.jjsc.Module;
import org.jjsc.utils.AssertUtils;
/**
 * This type of module searches for the compilation units in the file system directory.
 * Note that directory is not required to be a root classpath.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class FileSystemModule extends Module {
	private File location;

	public FileSystemModule(File location) {
		AssertUtils.assertDirectory(location, 
			location+"is not an existent directory");
		this.location = location;
	}

	@Override
	public String getName() {
		return location.getAbsolutePath();
	}

	@Override
	protected List<URL> getAllCompilationUnits() throws LoadModuleException {
		try {
			List<URL> result = new LinkedList<URL>();
			searchForCU(location,result);
			return Collections.unmodifiableList(result);
		}
		catch(MalformedURLException ex){
			throw new LoadModuleException(ex);
		}
	}

	private void searchForCU(File root, List<URL> result) throws MalformedURLException {
		for(String child : root.list()){
			if(child.equals("..")||
				child.equals(".")){
				continue;
			}
			File childPath = new File(root,child);
			if(childPath.isDirectory()){
				searchForCU(childPath,result);
			}
			else if(child.endsWith(CLASS_FILE_EXT)){
				result.add(childPath.toURL());
			}
		}
	}

}
