package org.seamlets.cms.admin.content.pagecontent;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.Local;

import org.seamlets.cms.entities.pagecomponent.CMSComponentHelper;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;


@Local
public interface PageContentProvider extends Serializable {

	public PageContentEntity getRootPageContentForPart(String viewPartId);
	
	public List<Entry<String, List<CMSComponentHelper>>> getComponentCategories();
	
}