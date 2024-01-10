package org.seamlets.cms.admin.contoll.component;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.event.AbortProcessingException;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeExpandedEvent;

@Stateful
@Name("componentTreeStateAdviser")
public class ComponentTreeStateAdviserBean implements ComponentTreeStateAdviser {

	private Set<Object>	openedNodes	= new HashSet<Object>();

	@Create
	public void create() {
	}

	@Destroy
	@Remove
	public void remove() {
	}

	@Override
	public Boolean adviseNodeOpened(UITree tree) {
		Object treeNode = tree.getRowData();
		return openedNodes.contains(treeNode);
	}

	@Override
	public Boolean adviseNodeSelected(UITree tree) {
		return null;
	}

	@Override
	public void processExpansion(NodeExpandedEvent nodeExpandedEvent) throws AbortProcessingException {
		HtmlTree tree = (HtmlTree) nodeExpandedEvent.getComponent();
		Object node = tree.getRowData();
		if(openedNodes.contains(node)) {
			openedNodes.remove(node);
		}
		else {
			openedNodes.add(node);
		}
	}

}
