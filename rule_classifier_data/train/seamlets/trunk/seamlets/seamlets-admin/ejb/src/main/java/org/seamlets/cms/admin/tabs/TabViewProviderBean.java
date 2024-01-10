package org.seamlets.cms.admin.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ejb.PostActivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.el.MethodExpression;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputText;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.log.Log;
import org.richfaces.component.html.HtmlDropDownMenu;
import org.richfaces.component.html.HtmlMenuItem;
import org.richfaces.component.html.HtmlSpacer;
import org.seamlets.annotation.CookieSavedState;
import org.seamlets.annotation.CookieValue;
import org.seamlets.annotation.LoadCookies;
import org.seamlets.annotation.SaveCookies;
import org.seamlets.cms.tab.CMSTabviewHelper;
import org.seamlets.cms.tab.TabView;
import org.seamlets.interceptor.cookie.SplitType;

@Stateful
@Name("tabViewProvider")
@AutoCreate
@CookieSavedState
@Scope(ScopeType.SESSION)
public class TabViewProviderBean implements TabViewProvider {

	@Logger
	private Log								log;

	@In
	private Map<String, CMSTabviewHelper>	tabViews;

	@In
	private Map<String, String>				messages;

	private List<TabViewAction>				actions	= new ArrayList<TabViewAction>();

	private transient HtmlDropDownMenu				dropDownMenu;

	@CookieValue(path = "/", maxAge = Integer.MAX_VALUE)
	private String							selectedTab;

	@CookieValue(split = ",", splitType = SplitType.SET, path = "/", maxAge = Integer.MAX_VALUE)
	private Set<String>						openedTabs;

	@Destroy
	@Remove
	@SaveCookies
	public void destroy() {
	}

	@Create
	@LoadCookies
	public void create() {
		Map<String, TabView> tabs = new HashMap<String, TabView>();

		for (Entry<String, CMSTabviewHelper> tabviewHelper : tabViews.entrySet()) {
			CMSTabviewHelper tabView = tabviewHelper.getValue();
			try {
				TabView instance = tabView.newInstance();
				org.seamlets.cms.annotations.TabView annotation = tabView.getAnnotation();
				instance.setAnnotation(annotation);
				instance.setTabLable(messages.get(annotation.tabLableKey()));

				TabViewAction action = new TabViewAction(instance);
				actions.add(action);
				tabs.put(instance.getTabId(), instance);
			} catch (InstantiationException e) {
				log.error("Unable to instanciate TabView Class!", e);
			} catch (IllegalAccessException e) {
				log.error("Unable to instanciate TabView Class!", e);
			}
		}
		Collections.sort(actions, TabViewAction.Comparator);

		if (openedTabs == null)
			openedTabs = new HashSet<String>();

		for (String tabId : openedTabs) {
			tabs.get(tabId).setRendered(true);
		}

		createMenu();
	}

	@SaveCookies
	public void close(int index) {
		TabViewAction action = actions.get(index);
		action.close();
		openedTabs.remove(action.getTabView().getTabId());
//		createMenu();
	}

	@SaveCookies
	public void open(int index) {
		TabViewAction action = actions.get(index);
		action.open();
		selectedTab = action.getTabView().getTabId();
		openedTabs.add(action.getTabView().getTabId());
//		createMenu();
	}

	@PostActivate
	public void createMenu() {
		dropDownMenu = new HtmlDropDownMenu();
		dropDownMenu.setValue(messages.get("admin.toolbar.menu.views"));

		int i = 0;
		MethodExpression methodExpression = null;
		for (TabViewAction action : actions) {
			HtmlMenuItem menuItem = new HtmlMenuItem();
			menuItem.setSubmitMode("ajax");
			menuItem.setReRender("tabContainer, menu");
			menuItem.setIcon("/img/admingui/useradmin.png");

			if (action.getTabView().isRendered()) {
				HtmlGraphicImage image = new HtmlGraphicImage();
				image.setValue("/img/admingui/check.png");
				image.setHeight("12");
				menuItem.getChildren().add(image);

				methodExpression = Expressions.instance().createMethodExpression(
						"#{tabViewProvider.close(" + i++ + ")}").toUnifiedMethodExpression();
			} else {
				HtmlSpacer spacer = new HtmlSpacer();
				spacer.setWidth("12");
				menuItem.getChildren().add(spacer);

				methodExpression = Expressions.instance()
						.createMethodExpression("#{tabViewProvider.open(" + i++ + ")}").toUnifiedMethodExpression();
			}

			menuItem.setActionExpression(methodExpression);

			HtmlOutputText text = new HtmlOutputText();
			text.setValue(action.getLable());
			menuItem.getChildren().add(text);

			dropDownMenu.getChildren().add(menuItem);

		}

	}

	public HtmlDropDownMenu getDropDownMenu() {
		return dropDownMenu;
	}

	public void setDropDownMenu(HtmlDropDownMenu dropDownMenu) {
		this.dropDownMenu = dropDownMenu;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	@SaveCookies
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public List<TabViewAction> getActions() {
		return actions;
	}

}
