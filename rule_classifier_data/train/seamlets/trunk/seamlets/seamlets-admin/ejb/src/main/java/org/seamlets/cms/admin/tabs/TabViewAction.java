package org.seamlets.cms.admin.tabs;

import java.util.Comparator;

import org.seamlets.cms.tab.TabView;

public class TabViewAction {

	public static final Comparator<TabViewAction>	Comparator	= new Comparator<TabViewAction>() {

																	@Override
																	public int compare(TabViewAction o1,
																			TabViewAction o2) {
																		return o1.getLable().compareTo(o2.getLable());
																	}

																};
	private TabView									tabView;

	public TabViewAction(TabView tabView) {
		this.tabView = tabView;
	}

	public void open() {
		tabView.setRendered(true);
	}

	public void close() {
		tabView.setRendered(false);
	}

	public String getLable() {
		return tabView.getTabLable();
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}
	
}
