package org.seamlets.cms.admin.contoll.page;

import java.io.Serializable;

import javax.ejb.Local;

import org.richfaces.component.state.TreeStateAdvisor;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedListener;

@Local
public interface PageStructureController extends Serializable, TreeStateAdvisor, NodeSelectedListener,
		NodeExpandedListener {
	
	public void create();

	public void remove();

	public String getSelectedTab();

	public void setSelectedTab(String selectedTab);
}
