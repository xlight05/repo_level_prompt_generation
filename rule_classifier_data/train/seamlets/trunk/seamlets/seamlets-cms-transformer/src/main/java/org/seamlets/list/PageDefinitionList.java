package org.seamlets.list;


import java.util.Arrays;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.cms.entities.PageDefinition;

@AutoCreate
@Name("pageDefinitionList")
public class PageDefinitionList extends EntityQuery<PageDefinition> {
	
	private static final String		EJBQL			= "SELECT p FROM PageDefinition p WHERE p.deleted IS NULL";

	private static final String[]	RESTRICTIONS	= {
		"p.domain = #{applicationConfiguration.domain}",
		"p.canonicalViewID = #{pageDefinitionList.pageDefinition.canonicalViewID}", };

	private PageDefinition				pageDefinition		= new PageDefinition();

	public PageDefinitionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PageDefinition getPageDefinition() {
		return pageDefinition;
	}
}
