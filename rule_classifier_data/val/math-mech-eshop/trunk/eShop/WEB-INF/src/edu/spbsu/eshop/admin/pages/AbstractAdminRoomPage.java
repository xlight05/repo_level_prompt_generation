package edu.spbsu.eshop.admin.pages;

import java.util.HashSet;
import java.util.Set;

import edu.spbsu.eshop.storage.data.Group;
import edu.spbsu.eshop.storage.data.Group.Permission;

public class AbstractAdminRoomPage implements AdminRoomPage {

    private String page;
    private String text;
    private OnPageRequestAction action;
    private String request;
    private Set<Permission> necessaryPermsissions = new HashSet<Permission>();

    /*
     * public AbstractAdminRoomPage(String request, String text, String page,
     * OnPageRequestAction onPageRequestAction) { this.action =
     * onPageRequestAction; this.request = request; this.text = text; this.page
     * = page; }
     */

    public AbstractAdminRoomPage(String request, String text, String page,
	    OnPageRequestAction onPageRequestAction,
	    Group.Permission... permissions) {
	this.action = onPageRequestAction;
	this.request = request;
	this.text = text;
	this.page = page;
	setNecessaryPermsissions(permissions);
    }

    // public AbstractAdminMenuItem() {}

    public OnPageRequestAction getAction() {
	return action;
    }

    /**
     * 
     * @param request
     *            if URL is .../admin/{request} then {page} will be shown
     * @param text
     *            text to show as a link on content page
     * @param page
     *            page to show on request
     */
    public AbstractAdminRoomPage(String request, String text, String page) {
	this.request = request;
	this.text = text;
	this.page = page;
    }

    public String getRequest() {
	return request;
    }

    public String getPage() {
	return page;
    }

    public Set<Group.Permission> getNecessaryPermsissions() {
	return this.necessaryPermsissions;
    }

    public void setNecessaryPermsissions(
	    Set<Group.Permission> necessaryPermsissions) {
	this.necessaryPermsissions = necessaryPermsissions;
    }

    public void setNecessaryPermsissions(Permission... permissions) {
	this.necessaryPermsissions = new HashSet<Permission>();
	for (Permission permission : permissions) {
	    necessaryPermsissions.add(permission);
	}

    }

    public String getText() {
	return text;
    }

    protected void setAction(OnPageRequestAction action) {
	this.action = action;
    }

}
