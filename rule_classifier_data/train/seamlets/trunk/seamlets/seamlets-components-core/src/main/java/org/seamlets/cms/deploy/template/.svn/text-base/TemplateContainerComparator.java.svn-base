package org.seamlets.cms.deploy.template;

import java.util.Comparator;

import org.seamlets.cms.xsd.template.Template;


public class TemplateContainerComparator implements Comparator<TemplateContainer> {

	@SuppressWarnings("null")
	public int compare(TemplateContainer temp1, TemplateContainer temp2) {
		if (temp1 == null && temp2 == null)
			return 0;
		if (temp1 != null && temp2 == null)
			return -1;
		if (temp1 == null && temp2 != null)
			return 1;

		Template t0 = temp1.getTemplate();
		Template t1 = temp2.getTemplate();
		if (t0 == null && t1 == null)
			return 0;
		if (t0 != null && t1 == null)
			return -1;
		if (t0 == null && t1 != null)
			return 1;

		String dN0 = temp1.getTemplate().getDisplayName();
		String dN1 = temp2.getTemplate().getDisplayName();
		if (dN0 == null && dN1 == null)
			return 0;
		if (dN0 != null && dN1 == null)
			return -1;
		if (dN0 == null && dN1 != null)
			return 1;

		return dN0.compareTo(dN1);
	}

}
