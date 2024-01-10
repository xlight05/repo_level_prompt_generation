package org.seamlets.cms.list;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.cms.entities.template.TemplateEntity;


@Name("templateList")
public class TemplateList extends EntityQuery<TemplateEntity> {

	private static final String		EJBQL			= "from TemplateEntity templateEntity";

	private static final String[]	RESTRICTIONS	= {};

	public TemplateList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public TemplateEntity getDomain() {
		return null;
	}
}
