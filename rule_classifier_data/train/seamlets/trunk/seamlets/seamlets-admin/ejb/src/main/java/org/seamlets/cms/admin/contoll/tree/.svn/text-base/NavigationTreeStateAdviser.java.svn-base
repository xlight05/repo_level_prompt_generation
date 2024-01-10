package org.seamlets.cms.admin.contoll.tree;

import java.io.Serializable;

import javax.ejb.Local;

import org.richfaces.component.state.TreeStateAdvisor;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedListener;
import org.seamlets.cms.entities.Domain;
import org.seamlets.cms.entities.NavigationFolder;
import org.seamlets.cms.entities.PageDefinition;


@Local
public interface NavigationTreeStateAdviser extends Serializable,
		TreeStateAdvisor, NodeSelectedListener, NodeExpandedListener {
	
	public void create();

	public void remove();

	public void setSelectedTreeNode(Domain domain);

	public void setSelectedTreeNode(NavigationFolder navigationFolder);
	
	public void setSelectedTreeNode(PageDefinition pageDefinition);
	
	public void clearSelection();

}
