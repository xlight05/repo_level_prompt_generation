package edu.spbsu.eshop.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.spbsu.eshop.admin.pages.AdminRoomPage;
import edu.spbsu.eshop.admin.pages.OnPageRequestAction;
import edu.spbsu.eshop.beans.ErrorBean;
import edu.spbsu.eshop.beans.SessionBean;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Group.Permission;

public class AdminPageRedirector {
    private AdminBean adminBean;
    private static final String ERROR_PAGE = "/error.jsf";

    public void setAdminBean(AdminBean adminBean) {
	this.adminBean = adminBean;
    }

    private String pageRequest;
    private SessionBean userBean;

    public String getPageRequest() {
	return pageRequest;
    }

    public boolean isUserHaveRightsToViewPage(AdminRoomPage page) {
	Customer user = userBean.getUser();
	Set<Permission> userPermissions = user.getGroup().getPermissions();
	return userPermissions.containsAll(page.getNecessaryPermsissions());
    }

    public void setPageRequest(String pageRequest) {
	this.pageRequest = pageRequest;
    }

    public String getPage() {
	AdminRoomPage page = adminBean.pages.get(pageRequest);
	if (!isUserHaveRightsToViewPage(page)) {
	    ErrorBean.setErrorMessage("you haven't enought rights");
	    return ERROR_PAGE;
	}

	OnPageRequestAction action = page.getAction();
	if (action != null)
	    action.pageRequested();
	return (page == null) ? ERROR_PAGE : page.getPage();
    }

    public AdminRoomPage[] getPages() {
	List<AdminRoomPage> list = new ArrayList<AdminRoomPage>();
	for (AdminRoomPage page : adminBean.pages.values()) {
	    if (page.getText() != null)
		if (isUserHaveRightsToViewPage(page))
		    list.add(page);
	}
	return (AdminRoomPage[]) list.toArray(new AdminRoomPage[0]);
    }

    public void setUserBean(SessionBean userBean) {
	this.userBean = userBean;
    }

}
