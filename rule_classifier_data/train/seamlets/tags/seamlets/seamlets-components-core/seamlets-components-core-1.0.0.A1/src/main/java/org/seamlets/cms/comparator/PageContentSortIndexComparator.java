package org.seamlets.cms.comparator;

import java.util.Comparator;

import org.seamlets.cms.entities.pagecomponent.PageContentEntity;



public class PageContentSortIndexComparator implements Comparator<PageContentEntity> {

	@SuppressWarnings("null")
	public int compare(PageContentEntity p0, PageContentEntity p1) {
		if(p0 == null && p1 == null) return 0;
		if(p0 != null && p1 == null) return -1;
		if(p0 == null && p1 != null) return 1;
		
		Integer index0 = p0.getSortIndex();
		Integer index1 = p1.getSortIndex();
		if(index0 == null && index1 == null) return 0;
		if(index0 != null && index1 == null) return -1;
		if(index0 == null && index1 != null) return 1;
		
		return index0.compareTo(index1);
		
	}

}
