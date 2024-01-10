package org.seamlets.cms.tab;

public class CMSTabviewHelper {

	private Class<? extends TabView>							clazz;

	private org.seamlets.cms.annotations.TabView	annotation;

	private String									tabId;

	public CMSTabviewHelper(Class<? extends TabView> clazz, org.seamlets.cms.annotations.TabView annotion) {
		this.clazz = clazz;
		this.annotation = annotion;
		this.tabId = annotion.tabId();
	}
	
	public TabView newInstance() throws InstantiationException, IllegalAccessException {
		TabView instance = clazz.newInstance();
		return instance;
	}

	public Class<? extends TabView> getClazz() {
		return clazz;
	}

	public void setClazz(Class<TabView> clazz) {
		this.clazz = clazz;
	}

	public org.seamlets.cms.annotations.TabView getAnnotation() {
		return annotation;
	}

	public void setAnnotation(org.seamlets.cms.annotations.TabView annotation) {
		this.annotation = annotation;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

}
