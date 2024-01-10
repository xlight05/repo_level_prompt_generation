package org.seamlets.cms.tab;

import java.net.URL;

public abstract class TabView {

	protected org.seamlets.cms.annotations.TabView	annotation;
	protected String								tabLable;
	protected boolean								rendered;

	public org.seamlets.cms.annotations.TabView getAnnotation() {
		return annotation;
	}

	public void setAnnotation(org.seamlets.cms.annotations.TabView annotation) {
		this.annotation = annotation;
	}

	public void setTabLable(String tabLable) {
		this.tabLable = tabLable;
	}

	public String getTabId() {
		return annotation.tabId();
	}

	public String getTabLable() {
		return this.tabLable;
	}

	public URL getTabViewId() {
		return getClass().getResource(annotation.tabViewId());
	}

	public boolean isRender() {
		return annotation.render();
	}

	public boolean isAddToMenu() {
		return annotation.addToMenu();
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
}
