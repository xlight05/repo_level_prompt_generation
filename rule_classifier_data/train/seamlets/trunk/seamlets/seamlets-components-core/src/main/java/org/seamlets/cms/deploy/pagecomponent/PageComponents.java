package org.seamlets.cms.deploy.pagecomponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;
import org.seamlets.cms.annotations.PageContent;
import org.seamlets.cms.entities.pagecomponent.CMSComponentHelper;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;


@Name("org.seamlets.cms.pageContentMapper")
@Scope(ScopeType.APPLICATION)
@Startup
public class PageComponents implements Serializable {

	@Logger
	private Log										log;

	@In("#{deploymentStrategy.annotatedClasses['org.seamlets.cms.annotations.PageContent']}")
	private Set<Class<? extends PageContentEntity>>	pageContentClasses;

//	@In("#{deploymentStrategy.deploymentHandlers['org.seamlets.cms.deployment.DotPageContentDotXmlDeploymentHandler']}")
//	private DotPageContentDotXmlDeploymentHandler	dotPageContentDotXmlDeploymentHandler;
	
	@In(required = false)
	@Out
	private Map<String, List<CMSComponentHelper>>						componentCategories;

	@Create
	public void create() {
		componentCategories = new HashMap<String, List<CMSComponentHelper>>();
		
		if(pageContentClasses != null) {
			for (Class<? extends PageContentEntity> clazz : pageContentClasses) {
				handleClass(clazz);
			}
		}
//
//		if(dotPageContentDotXmlDeploymentHandler != null) {
//			for (FileDescriptor is : dotPageContentDotXmlDeploymentHandler.getResources()) {
//				handlePagecontentXml(is);
//			}
//		}
	}

//	private void handlePagecontentXml(FileDescriptor is) {
//		log.info("Scanned .pagecontent.xml: #0", is.getUrl());
//	}

	private void handleClass(Class<? extends PageContentEntity> clazz) {
		PageContent pageContent = clazz.getAnnotation(PageContent.class);
		
		String category = pageContent.category();
		List<CMSComponentHelper> categoryList = componentCategories.get(category);
		if(categoryList == null) {
			categoryList = new ArrayList<CMSComponentHelper>();
			componentCategories.put(category, categoryList);
		}
		
		CMSComponentHelper componentHelper = new CMSComponentHelper(pageContent, clazz);
		categoryList.add(componentHelper);
		
		log.info("Pagecontent: #0, category: #1, type: #2, editViewId: #3", pageContent.displayName(),
				category, clazz.getCanonicalName(), pageContent.editViewId());
	}

}
