package org.seamlets.cms.components.tab.resource;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.event.AbortProcessingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeRowKey;
import org.seamlets.cms.annotations.TabView;
import org.seamlets.resource.entity.Directory;
import org.seamlets.resource.entity.File;
import org.seamlets.resource.home.DirectoryHome;
import org.seamlets.resource.home.FileHome;

@Stateful
@Name("resourceTab")
@TabView(tabId = "resource", tabLableKey = "tab.resource", tabViewId = "resourceTab.xhtml")
public class ResourceTabView extends org.seamlets.cms.tab.TabView implements IResourceTabView {

	@In
	private DirectoryHome	directoryHome;

	@In
	private FileHome		fileHome;

	@In
	private EntityManager	resourceEntityManager;

	private boolean			showDialog;

	private String			directoryName;

	private Object			selectedNode;
	private Directory		selectedDirectory;
	private File			selectedFile;

	private Directory[]		roots		= new Directory[1];

	private Set<Object>		openedNodes	= new HashSet<Object>();

	private Query			directoryQuery;

	private Query			fileQuery;

	@Create
	@Begin(join = true)
	public void create() {
		directoryQuery = resourceEntityManager.createNamedQuery("resource.directory.root");
		fileQuery = resourceEntityManager.createNamedQuery("resource.file.root");

		Directory root = new Directory();
		root.setName("/");
		roots[0] = root;

		selectedNode = root;
		selectedDirectory = root;

		refreshRoot();
	}

	@SuppressWarnings("unchecked")
	private void refreshRoot() {
		List<Directory> rootDirectories = directoryQuery.getResultList();

		List<File> rootFiles = fileQuery.getResultList();

		roots[0].setDirectories(rootDirectories);
		roots[0].setFiles(rootFiles);
	}

	@Remove
	@Destroy
	public void remove() {
	}

	public Directory[] getRoots() {
		return roots;
	}

	@SuppressWarnings("unchecked")
	public Boolean adviseNodeOpened(UITree tree) {
		TreeRowKey<Integer> treeRowKey = (TreeRowKey<Integer>) tree.getRowKey();
		Object treeNode = tree.getRowData();
		if (treeRowKey == null || treeRowKey.depth() <= 1
				|| (treeNode instanceof Directory && openedNodes.contains(treeNode))) {
			return Boolean.TRUE;
		}
		return null;
	}

	public Boolean adviseNodeSelected(UITree tree) {
		Object treeNode = tree.getRowData();
		if (treeNode == selectedNode) {
			return Boolean.TRUE;
		}
		return null;
	}

	public void processExpansion(NodeExpandedEvent nodeExpandedEvent) throws AbortProcessingException {
		HtmlTree tree = (HtmlTree) nodeExpandedEvent.getComponent();
		Object node = tree.getRowData();
		if (openedNodes.contains(node)) {
			openedNodes.remove(node);
		} else {
			openedNodes.add(node);
		}
	}

	public void processSelection(NodeSelectedEvent nodeSelectedEvent) throws AbortProcessingException {
		HtmlTree tree = (HtmlTree) nodeSelectedEvent.getComponent();

		if (!tree.isRowAvailable())
			return;

		clearSelection();
		selectedNode = tree.getRowData();

		if (selectedNode instanceof Directory) {
			selectedDirectory = (Directory) selectedNode;
		} else if (selectedNode instanceof File) {
			selectedFile = (File) selectedNode;
		}
	}

	public void clearSelection() {
		selectedNode = null;
		selectedFile = null;
		selectedDirectory = null;
	}

	@Override
	public InputStream getDirectoryImage() {
		return getClass().getResourceAsStream("Folder.png");
	}

	@Override
	public InputStream getRootImage() {
		return getClass().getResourceAsStream("Hd.png");
	}

	public Directory getSelectedDirectory() {
		return selectedDirectory;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	@Override
	public InputStream getDeleteImage() {
		return getClass().getResourceAsStream("delete.png");
	}

	@Override
	public InputStream getDeleteImageDis() {
		return getClass().getResourceAsStream("delete_dis.png");
	}

	@Override
	public boolean isRoot() {
		return selectedNode == roots[0];
	}

	@Override
	public void showDirectoryDialog() {
		directoryName = null;
		showDialog = true;
	}

	@Override
	public void hideDirectoryDialog() {
		showDialog = false;
	}

	@Override
	public void addDirectory() {
		showDialog = false;
		Directory directory = new Directory();
		directory.setName(directoryName);

		if (selectedDirectory != roots[0])
			directory.setParent(selectedDirectory);

		resourceEntityManager.persist(directory);

		if (selectedDirectory != null && selectedDirectory != roots[0]) {
			openedNodes.add(selectedDirectory);
			resourceEntityManager.refresh(selectedDirectory);
		} else if (selectedDirectory != null) {
			refreshRoot();
		}
		selectedNode = directory;
		selectedDirectory = directory;

	}

	@Override
	public void deleteItem() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isShowDialog() {
		return showDialog;
	}

	@Override
	public String getDirectoryName() {
		return directoryName;
	}

	@Override
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
}
