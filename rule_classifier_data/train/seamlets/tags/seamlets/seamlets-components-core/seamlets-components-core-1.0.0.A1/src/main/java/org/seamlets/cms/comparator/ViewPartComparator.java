package org.seamlets.cms.comparator;

import java.util.Comparator;

import org.seamlets.cms.entities.viewpart.ViewPart;

public class ViewPartComparator implements Comparator<ViewPart> {

	@SuppressWarnings("null")
	public int compare(ViewPart part0, ViewPart part1) {
		if (part0 == null && part1 == null)
			return 0;
		if (part0 != null && part1 == null)
			return -1;
		if (part0 == null && part1 != null)
			return 1;

		Integer sort0 = part0.getSortorder();
		Integer sort1 = part1.getSortorder();
		if (sort0 == null && sort1 == null)
			return 0;
		if (sort0 != null && sort1 == null)
			return -1;
		if (sort0 == null && sort1 != null)
			return 1;

		return sort0.compareTo(sort1);
	}
}
