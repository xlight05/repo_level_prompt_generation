package org.seamlets.page;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.navigation.Pages;

@Name("pageInfo")
@Scope(ScopeType.PAGE)
public class PageInfoProvider implements Serializable {

	@In
	private FacesContext		facesContext;

	@In
	private Map<String, String>	messages;

	// @In
	// private IApplicationConfiguration applicationconfig;

	@Logger
	private Log					log;

	private String				pageKey;

	private String				windowTitleKey;

	private String				pageTitleKey;

	private String				pageSubtitleKey;

	private boolean				showToolBar			= true;

	private boolean				showNavigationView	= true;

	private boolean				showGlobaleMessages	= true;

	private boolean				showFooter			= true;

	@Create
	public void create() {
		String viewID = Pages.getViewId(facesContext);
		log.info("view id is #0", viewID);
		viewID = viewID.substring(1, viewID.lastIndexOf('.'));
		pageKey = viewID.replace("/", ".");
		windowTitleKey = "window." + pageKey + ".title";
		pageTitleKey = "page." + pageKey + ".title";
		pageSubtitleKey = "page." + pageKey + ".subtitle";

		// showLogout = applicationconfig.isShowLogin();
	}

	public String getPageKey() {
		return this.pageKey;
	}

	public String getWindowTitleKey() {
		return this.windowTitleKey;
	}

	public String getPageTitleKey() {
		return this.pageTitleKey;
	}

	public String getPageSubtitleKey() {
		return this.pageSubtitleKey;
	}

	public String getWindowTitle() {
		String value = messages.get(windowTitleKey);
		if (windowTitleKey.equals(value)) {
			return messages.get("window.default.title");
		}
		return value;
	}

	public String getPageTitle() {
		return messages.get(pageTitleKey);
	}

	public String getPageSubtitle() {
		return messages.get(pageSubtitleKey);
	}

	public boolean isShowNavigationView() {
		return showNavigationView;
	}

	public void setShowNavigationView(boolean showNavigationView) {
		this.showNavigationView = showNavigationView;
	}

	public boolean isShowGlobaleMessages() {
		return showGlobaleMessages;
	}

	public void setShowGlobaleMessages(boolean showGlobaleMessages) {
		this.showGlobaleMessages = showGlobaleMessages;
	}

	public boolean isShowFooter() {
		return showFooter;
	}

	public void setShowFooter(boolean showFooter) {
		this.showFooter = showFooter;
	}

	public boolean isShowToolBar() {
		return showToolBar;
	}

	public void setShowToolBar(boolean showToolBar) {
		this.showToolBar = showToolBar;
	}

}
