package org.seamlets.cms.comparator;

import java.util.Comparator;

import org.seamlets.cms.entities.NavigationFolder;


public class NavigationFolderComparator implements Comparator<NavigationFolder> {

	@SuppressWarnings("null")
	public int compare(NavigationFolder folder0, NavigationFolder folder1) {
		if(folder0 == null && folder1 == null) return 0;
		if(folder0 != null && folder1 == null) return -1;
		if(folder0 == null && folder1 != null) return 1;
		
		String name0 = folder0.getName();
		String name1 = folder1.getName();
		if(name0 == null && name1 == null) return 0;
		if(name0 != null && name1 == null) return -1;
		if(name0 == null && name1 != null) return 1;
		
		return name0.compareTo(name1);
	}
}