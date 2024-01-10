package org.zls.client;

import java.util.ArrayList;

import org.zls.client.Tab.TabInfo;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class TabList extends Composite {

	private class MouseLink extends Hyperlink {

		private int index;

		public MouseLink(String name, int index) {
			super(name, name);
			this.index = index;
			sinkEvents(Event.MOUSEEVENTS);
		}

		public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEOVER:
				mouseOver(index);
				break;
			case Event.ONMOUSEOUT:
				mouseOut(index);
				break;
			}

			super.onBrowserEvent(event);
		}
	}

	private HorizontalPanel list = new HorizontalPanel();
	private ArrayList tabs = new ArrayList();

	private int selectedTab = -1;

	public TabList(Tab.Images images) {
		initWidget(list);
		list.add(images.gwtLogo().createImage());
		setStyleName("hu-List");
	}

	public void addTab(final TabInfo info) {
		String name = info.getName();
		int index = list.getWidgetCount() - 1;

		MouseLink link = new MouseLink(name, index);
		list.add(link);
		tabs.add(info);

		list.setCellVerticalAlignment(link, HorizontalPanel.ALIGN_BOTTOM);
		styleTab(index, false);
	}

	public TabInfo find(String tabName) {
		for (int i = 0; i < tabs.size(); i++) {
			TabInfo info = (TabInfo) tabs.get(i);
			if (info.getName().equals(tabName)) {
				return info;
			}
		}

		return null;
	}

	public void setTabSelection(String name) {
		if (selectedTab != -1) {
			styleTab(selectedTab, false);
		}

		for (int i = 0; i < tabs.size(); i++) {
			TabInfo info = (TabInfo) tabs.get(i);
			if (info.getName().equals(name)) {
				selectedTab = i;
				styleTab(selectedTab, true);
				return;
			}
		}
	}

	private void colorTab(int index, boolean on) {
		String color = "";
		if (on) {
			color = ((TabInfo) tabs.get(index)).getColor();
		}

		Widget w = list.getWidget(index + 1);
		DOM.setStyleAttribute(w.getElement(), "backgroundColor", color);
	}

	private void styleTab(int index, boolean selected) {
		String style = (index == 0) ? "hu-FirstTabItem" : "hu-TabItem";

		if (selected) {
			style += "-selected";
		}

		Widget w = list.getWidget(index + 1);
		w.setStyleName(style);

		colorTab(index, selected);
	}

	private void mouseOut(int index) {
		if (index != selectedTab) {
			colorTab(index, false);
		}
	}

	private void mouseOver(int index) {
		if (index != selectedTab) {
			colorTab(index, true);
		}
	}
}
