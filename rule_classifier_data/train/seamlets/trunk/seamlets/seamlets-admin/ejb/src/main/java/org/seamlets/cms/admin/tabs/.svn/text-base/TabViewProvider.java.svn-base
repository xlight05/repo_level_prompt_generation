package org.seamlets.cms.admin.tabs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import org.richfaces.component.html.HtmlDropDownMenu;

@Local
public interface TabViewProvider extends Serializable{

	public void destroy();

	public void create();

	public void close(int index);

	public void open(int index);

	public HtmlDropDownMenu getDropDownMenu();

	public void setDropDownMenu(HtmlDropDownMenu dropDownMenu);

	public String getSelectedTab();

	public void setSelectedTab(String selectedTab);

	public List<TabViewAction> getActions();

	
}