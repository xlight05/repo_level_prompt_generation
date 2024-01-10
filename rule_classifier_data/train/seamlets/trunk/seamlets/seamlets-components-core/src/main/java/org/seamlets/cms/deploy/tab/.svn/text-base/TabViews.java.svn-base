package org.seamlets.cms.deploy.tab;

import java.io.Serializable;
import java.util.HashMap;
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
import org.seamlets.cms.tab.CMSTabviewHelper;
import org.seamlets.cms.tab.TabView;

@Name("org.seamlets.cms.tabViewMapper")
@Scope(ScopeType.APPLICATION)
@Startup
public class TabViews implements Serializable {

	@Logger
	private Log								log;
	
	@In("#{deploymentStrategy.annotatedClasses['org.seamlets.cms.annotations.TabView']}")
	private Set<Class<? extends TabView>>	tabViewClasses;

	@In(required = false)
	@Out
	private Map<String, CMSTabviewHelper>	tabViews;

	@Create
	public void startup() {
		tabViews = new HashMap<String, CMSTabviewHelper>();
		if (tabViewClasses != null) {
			for (Class<? extends TabView> clazz : tabViewClasses) {
				handleClass(clazz);
			}
		}
	}

	private void handleClass(Class<? extends TabView> clazz) {
		org.seamlets.cms.annotations.TabView tabViewAnnotation = clazz
				.getAnnotation(org.seamlets.cms.annotations.TabView.class);

		String tabId = tabViewAnnotation.tabId();
		if (tabViews.containsKey(tabId)) {
			CMSTabviewHelper tab = tabViews.get(tabId);
			log.error("TabViewId '#0' must be unique. Found class '#1' annotated with this ID. Skip tabview '#2'", tabId, tab.getClazz()
					.getCanonicalName(), clazz.getCanonicalName());
			return;
		}
		
		CMSTabviewHelper tabViewHelper = new CMSTabviewHelper(clazz, tabViewAnnotation);
		tabViews.put(tabId, tabViewHelper);

		log.info("Tabview ID: #0, Class: #1", tabId, clazz.getCanonicalName());
	}
}
