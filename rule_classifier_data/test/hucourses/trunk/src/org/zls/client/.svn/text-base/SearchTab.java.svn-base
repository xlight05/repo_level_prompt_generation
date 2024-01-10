package org.zls.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchTab extends Tab {

	CourseTable results;
	VerticalPanel panel;

	public static TabInfo init() {
		return new TabInfo("Search", "<h2> Search Page! </h2>") {

			public Tab createInstance() {
				return new SearchTab();
			}
		};
	}

	public SearchTab() {

		panel = new VerticalPanel();
		results = new CourseTable();
		Button button = new Button("Refresh");
		button.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				results.refresh();
			}
			
		});
		
//		Window.addWindowResizeListener(new WindowResizeListener() {
//
//			public void onWindowResized(int width, int height) {
//				results.setWidth(width);
//				results.doLayout();
//				
//			}
//			
//		});
//		
//		
		
		panel.add(results);
		panel.add(button);
		initWidget(panel);
	}

	public void onShow() {

	}
}
