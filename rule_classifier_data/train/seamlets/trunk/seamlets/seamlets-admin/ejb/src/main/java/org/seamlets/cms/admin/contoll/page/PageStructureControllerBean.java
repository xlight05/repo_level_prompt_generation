package org.seamlets.cms.admin.contoll.page;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.event.AbortProcessingException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;

@Stateful
@Name("pageStructureController")
public class PageStructureControllerBean implements PageStructureController {

	@In(required = false, scope = ScopeType.CONVERSATION)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private PageContentEntity	selectedComponent;
	
	private String selectedTab;
	
	private Set<Object>	openedNodes	= new HashSet<Object>();
	
	@Create
	public void create() {
	}

	@Destroy
	@Remove
	public void remove() {
	}

	public void processSelection(NodeSelectedEvent nodeSelectedEvent) throws AbortProcessingException {
		HtmlTree tree = (HtmlTree) nodeSelectedEvent.getComponent();
		
		if(!tree.isRowAvailable())
			return;
		
		Object selectedNode = tree.getRowData();

		if (selectedNode instanceof PageContentEntity) {
			selectedComponent = (PageContentEntity) selectedNode;
		}
		else {
			selectedComponent = null;
		}
	}
	
	public Boolean adviseNodeOpened(UITree tree) {
		Object treeNode = tree.getRowData();
		return openedNodes.contains(treeNode);
	}

	@Override
	public Boolean adviseNodeSelected(UITree tree) {
		Object treeNode = tree.getRowData();
		return treeNode == selectedComponent;
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

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

}
