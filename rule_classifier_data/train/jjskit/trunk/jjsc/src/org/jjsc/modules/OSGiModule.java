package org.jjsc.modules;

import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.jjsc.Module;
import org.jjsc.utils.AssertUtils;
import org.osgi.framework.Bundle;
/**
 * This implementation delegates compilation units resolving to the OSGi
 * bundle passed.
 * @author alex.bereznevatiy@gmail.com
 *
 */
public class OSGiModule extends Module {
	private Bundle bundle;

	public OSGiModule(Bundle bundle) {
		AssertUtils.assertTrue(bundle!=null&&
				bundle.getState()!=Bundle.UNINSTALLED,
			"Bundle is either NULL or uninstalled");
		this.bundle = bundle;
	}

	@Override
	public String getName() {
		return bundle.getSymbolicName()+'_'+bundle.getVersion();
	}

	@Override
	protected List<URL> getAllCompilationUnits() {
		@SuppressWarnings("unchecked")
		Enumeration<URL> entries = bundle.findEntries("/", 
				"*"+CLASS_FILE_EXT, true);
		List<URL> result = new LinkedList<URL>();
		while(entries!=null&&entries.hasMoreElements()){
			result.add(entries.nextElement());
		}
		return Collections.unmodifiableList(result);
	}

}
