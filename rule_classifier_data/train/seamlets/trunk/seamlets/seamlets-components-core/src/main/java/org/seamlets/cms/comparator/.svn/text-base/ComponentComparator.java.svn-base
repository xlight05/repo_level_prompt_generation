package org.seamlets.cms.comparator;

import java.util.Comparator;

import org.seamlets.cms.entities.pagecomponent.PageContentEntity;

public class ComponentComparator implements Comparator<PageContentEntity> {

	@SuppressWarnings("null")
	public int compare(PageContentEntity component0, PageContentEntity component1) {
		if (component0 == null && component1 == null)
			return 0;
		if (component0 != null && component1 == null)
			return -1;
		if (component0 == null && component1 != null)
			return 1;
		

		Integer sort0 = component0.getSortIndex();
		Integer sort1 = component1.getSortIndex();
		if (sort0 == null && sort1 == null)
			return 0;
		if (sort0 != null && sort1 == null)
			return -1;
		if (sort0 == null && sort1 != null)
			return 1;

		return sort0.compareTo(sort1);
	}
}
