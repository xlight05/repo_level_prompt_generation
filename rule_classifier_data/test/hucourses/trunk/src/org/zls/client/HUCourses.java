package org.zls.client;

import org.zls.client.Tab.TabInfo;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HUCourses implements EntryPoint, HistoryListener {

	private static final Tab.Images images = (Tab.Images) GWT
			.create(Tab.Images.class);

	protected TabList list = new TabList(images);
	protected SearchBox box = new SearchBox();
	private TabInfo curInfo;
	private Tab curTab;
	private HTML description = new HTML();
	private VerticalPanel panel = new VerticalPanel();

	public void onHistoryChanged(String token) {

		TabInfo info = list.find(token);
		if (info == null) {
			showInfo();
			return;
		}

		show(info, false);
	}

	public void onModuleLoad() {
		loadTabs();

		panel.add(box);
		panel.setCellHorizontalAlignment(box, HorizontalPanel.ALIGN_CENTER);
		panel.add(list);
		panel.add(description);
		panel.setWidth("100%");

		description.setStyleName("hu-Info");

		History.addHistoryListener(this);
		RootPanel.get().add(panel);

		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
	}

	public void show(TabInfo info, boolean affectHistory) {
		if (info == curInfo) {
			return;
		}

		curInfo = info;

		if (curTab != null) {
			curTab.onHide();
			panel.remove(curTab);
		}

		curTab = info.getInstance();
		list.setTabSelection(info.getName());
		description.setHTML(info.getDescription());

		if (affectHistory) {
			History.newItem(info.getName());
		}

		DOM.setStyleAttribute(description.getElement(), "backgroundColor", info
				.getColor());

		curTab.setVisible(false);
		panel.add(curTab);
		panel.setCellHorizontalAlignment(curTab, VerticalPanel.ALIGN_CENTER);
		curTab.setVisible(true);
		curTab.onShow();
	}

	protected void loadTabs() {
		list.addTab(SearchTab.init());
	}

	private void showInfo() {
		show(list.find("Search"), false);
	}

	// /**
	// * This is the entry point method.
	// */
	// public void onModuleLoad() {
	// Image img = new Image(
	// "http://code.google.com/webtoolkit/logo-185x175.png");
	// Button button = new Button("Click me");
	//
	// VerticalPanel vPanel = new VerticalPanel();
	// // We can add style names.
	// vPanel.addStyleName("widePanel");
	// vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	// vPanel.add(img);
	// vPanel.add(button);
	//
	// // Add image and button to the RootPanel
	// RootPanel.get().add(vPanel);
	//
	// // Create the dialog box
	// final DialogBox dialogBox = new DialogBox();
	//
	// dialogBox.setAnimationEnabled(true);
	// Button closeButton = new Button("close");
	// VerticalPanel dialogVPanel = new VerticalPanel();
	// dialogVPanel.setWidth("100%");
	// dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	// dialogVPanel.add(closeButton);
	//
	// closeButton.addClickListener(new ClickListener() {
	// public void onClick(Widget sender) {
	// dialogBox.hide();
	// }
	// });
	//
	// // Set the contents of the Widget
	// dialogBox.setWidget(dialogVPanel);
	//
	// button.addClickListener(new ClickListener() {
	// public void onClick(Widget sender) {
	// Server.getInstance().randomFunction(new AsyncCallback() {
	//
	// public void onFailure(Throwable caught) {
	// dialogBox.setText("FAILURE YOU SUCK!!");
	// System.out.println(GWT.getModuleBaseURL());
	// }
	//
	// public void onSuccess(Object result) {
	// dialogBox.setText((String) result);
	// }
	// });
	// dialogBox.center();
	// dialogBox.show();
	// }
	// });
	// }
}
