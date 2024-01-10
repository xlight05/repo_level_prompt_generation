package org.seamlets.cms.admin.contoll.tree;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.event.AbortProcessingException;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeRowKey;
import org.seamlets.cms.entities.Domain;
import org.seamlets.cms.entities.NavigationFolder;
import org.seamlets.cms.entities.PageDefinition;


@Stateful
@AutoCreate
@Name("navigationTreeStateAdviser")
public class NavigationTreeStateAdviserBean implements NavigationTreeStateAdviser {
	
	@Out(required = false)
	private Object					selectedNode;

	@Out(required = false)
	private Domain					selectedDomain;

	@Out(required = false)
	private NavigationFolder		selectedFolder;

	@Out(required = false)
	private PageDefinition			selectedPage;

	private Set<Object>	openedNodes	= new HashSet<Object>();

	@Create
	public void create() {
	}

	@Destroy
	@Remove
	public void remove() {
	}

	@SuppressWarnings("unchecked")
	public Boolean adviseNodeOpened(UITree tree) {
		TreeRowKey<Integer> treeRowKey = (TreeRowKey<Integer>) tree.getRowKey();
		Object treeNode = tree.getRowData();
		if (treeRowKey == null || treeRowKey.depth() <= 2
				|| (treeNode instanceof NavigationFolder && openedNodes.contains(treeNode))) {
			return Boolean.TRUE;
		}
		return null;
	}

	public Boolean adviseNodeSelected(UITree tree) {
		Object treeNode = tree.getRowData();
		if(treeNode == selectedNode) {
			return Boolean.TRUE;
		}
		return null;
	}

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

	public void processSelection(NodeSelectedEvent nodeSelectedEvent) throws AbortProcessingException {
		HtmlTree tree = (HtmlTree) nodeSelectedEvent.getComponent();
		
		if(!tree.isRowAvailable())
			return;
		
		clearSelection();
		selectedNode = tree.getRowData();

		if (selectedNode instanceof Domain) {
			selectedDomain = (Domain) selectedNode;
		} else if (selectedNode instanceof NavigationFolder) {
			selectedFolder = (NavigationFolder) selectedNode;
		} else if (selectedNode instanceof PageDefinition) {
			selectedPage = (PageDefinition) selectedNode;
		}
	}

	public Object getSelectedNode() {
		return selectedNode;
	}

	public Domain getSelectedDomain() {
		return selectedDomain;
	}

	public NavigationFolder getSelectedFolder() {
		return selectedFolder;
	}

	public PageDefinition getSelectedPage() {
		return selectedPage;
	}

	public boolean isDomainSelected() {
		return selectedDomain != null;
	}

	public boolean isPageSelected() {
		return selectedPage != null;
	}

	public boolean isFolderSelected() {
		return selectedFolder != null;
	}

	public void setSelectedTreeNode(Domain domain) {
		clearSelection();
		selectedDomain = domain;
		selectedNode = domain;
	}

	public void setSelectedTreeNode(NavigationFolder navigationFolder) {
		clearSelection();
		selectedFolder = navigationFolder;
		selectedNode = navigationFolder;
	}
	
	public void setSelectedTreeNode(PageDefinition pageDefinition) {
		clearSelection();
		selectedPage = pageDefinition;
		selectedNode = pageDefinition;
	}

	public void clearSelection() {
		selectedNode = null;
		selectedDomain = null;
		selectedFolder = null;
		selectedPage = null;
	}

}
