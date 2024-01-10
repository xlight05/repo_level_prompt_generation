package org.seamlets.cms.comparator;

import java.util.Comparator;

import org.seamlets.cms.entities.PageDefinition;



public class PageDefinitionComparator implements Comparator<PageDefinition> {

	@SuppressWarnings("null")
	public int compare(PageDefinition page0, PageDefinition page1) {
		if(page0 == null && page1 == null) return 0;
		if(page0 != null && page1 == null) return -1;
		if(page0 == null && page1 != null) return 1;
		
		String viewId0 = page0.getViewID();
		String viewId1 = page1.getViewID();
		if(viewId0 == null && viewId1 == null) return 0;
		if(viewId0 != null && viewId1 == null) return -1;
		if(viewId0 == null && viewId1 != null) return 1;
		
		return viewId0.compareTo(viewId1);
	}
}
